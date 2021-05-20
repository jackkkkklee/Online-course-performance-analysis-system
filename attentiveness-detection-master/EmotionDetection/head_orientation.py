

import os

import cv2

import numpy as np

import dlib

import time

import math

data_dir = r"E:\aut\RD\project\attentiveness-detection-master\test_datasets"

save_dir = r"...\results"

detector = dlib.get_frontal_face_detector()

predictor = dlib.shape_predictor(r"D:\attentiveness-detection-master\gaze_tracking\trained_models\shape_predictor_68_face_landmarks.dat")

POINTS_NUM_LANDMARK = 68


# 获取最大的人脸

def _largest_face(dets):
    if len(dets) == 1:
        return 0

    face_areas = [(det.right() - det.left()) * (det.bottom() - det.top()) for det in dets]

    largest_area = face_areas[0]

    largest_index = 0

    for index in range(1, len(dets)):

        if face_areas[index] > largest_area:
            largest_index = index

            largest_area = face_areas[index]

    print("largest_face index is {} in {} faces".format(largest_index, len(dets)))

    return largest_index


# 从dlib的检测结果抽取姿态估计需要的点坐标

def get_image_points_from_landmark_shape(landmark_shape):
    if landmark_shape.num_parts != POINTS_NUM_LANDMARK:
        print("ERROR:landmark_shape.num_parts-{}".format(landmark_shape.num_parts))

        return -1, None

    # 2D image points. If you change the image, you need to change vector

    image_points = np.array([

        (landmark_shape.part(17).x, landmark_shape.part(17).y),  # 17 left brow left corner

        (landmark_shape.part(21).x, landmark_shape.part(21).y),  # 21 left brow right corner

        (landmark_shape.part(22).x, landmark_shape.part(22).y),  # 22 right brow left corner

        (landmark_shape.part(26).x, landmark_shape.part(26).y),  # 26 right brow right corner

        (landmark_shape.part(36).x, landmark_shape.part(36).y),  # 36 left eye left corner

        (landmark_shape.part(39).x, landmark_shape.part(39).y),  # 39 left eye right corner

        (landmark_shape.part(42).x, landmark_shape.part(42).y),  # 42 right eye left corner

        (landmark_shape.part(45).x, landmark_shape.part(45).y),  # 45 right eye right corner

        (landmark_shape.part(31).x, landmark_shape.part(31).y),  # 31 nose left corner

        (landmark_shape.part(35).x, landmark_shape.part(35).y),  # 35 nose right corner

        (landmark_shape.part(48).x, landmark_shape.part(48).y),  # 48 mouth left corner

        (landmark_shape.part(54).x, landmark_shape.part(54).y),  # 54 mouth right corner

        (landmark_shape.part(57).x, landmark_shape.part(57).y),  # 57 mouth central bottom corner

        (landmark_shape.part(8).x, landmark_shape.part(8).y),  # 8 chin corner

    ], dtype="double")

    return 0, image_points


# 用dlib检测关键点，返回姿态估计需要的几个点坐标

def get_image_points(img):
    gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)  # 图片调整为灰色

    dets = detector(img, 0)

    if 0 == len(dets):
        print("ERROR: found no face")

        return -1, None

    largest_index = _largest_face(dets)

    face_rectangle = dets[largest_index]

    landmark_shape = predictor(img, face_rectangle)

    return get_image_points_from_landmark_shape(landmark_shape)


# 获取旋转向量和平移向量

def get_pose_estimation(img_size, image_points):
    # 3D model points.

    model_points = np.array([

        (6.825897, 6.760612, 4.402142),  # 33 left brow left corner

        (1.330353, 7.122144, 6.903745),  # 29 left brow right corner

        (-1.330353, 7.122144, 6.903745),  # 34 right brow left corner

        (-6.825897, 6.760612, 4.402142),  # 38 right brow right corner

        (5.311432, 5.485328, 3.987654),  # 13 left eye left corner

        (1.789930, 5.393625, 4.413414),  # 17 left eye right corner

        (-1.789930, 5.393625, 4.413414),  # 25 right eye left corner

        (-5.311432, 5.485328, 3.987654),  # 21 right eye right corner

        (2.005628, 1.409845, 6.165652),  # 55 nose left corner

        (-2.005628, 1.409845, 6.165652),  # 49 nose right corner

        (2.774015, -2.080775, 5.048531),  # 43 mouth left corner

        (-2.774015, -2.080775, 5.048531),  # 39 mouth right corner

        (0.000000, -3.116408, 6.097667),  # 45 mouth central bottom corner

        (0.000000, -7.415691, 4.070434)  # 6 chin corner

    ])

    # Camera internals

    focal_length = img_size[1]

    center = (img_size[1] / 2, img_size[0] / 2)

    camera_matrix = np.array(

        [[focal_length, 0, center[0]],

         [0, focal_length, center[1]],

         [0, 0, 1]], dtype="double"

    )

    dist_coeffs = np.array([7.0834633684407095e-002, 6.9140193737175351e-002, 0.0, 0.0, -1.3073460323689292e+000],
                           dtype="double")  # Assuming no lens distortion

    (success, rotation_vector, translation_vector) = cv2.solvePnP(model_points, image_points, camera_matrix,
                                                                  dist_coeffs, flags=cv2.SOLVEPNP_ITERATIVE)

    # print("Rotation Vector:\n {}".format(rotation_vector))

    # print("Translation Vector:\n {}".format(translation_vector))

    return success, rotation_vector, translation_vector, camera_matrix, dist_coeffs


# 从旋转向量转换为欧拉角

def get_euler_angle(rotation_vector):
    # calculate rotation angles

    theta = cv2.norm(rotation_vector, cv2.NORM_L2)

    # transformed to quaterniond

    w = math.cos(theta / 2)

    x = math.sin(theta / 2) * rotation_vector[0][0] / theta

    y = math.sin(theta / 2) * rotation_vector[1][0] / theta

    z = math.sin(theta / 2) * rotation_vector[2][0] / theta

    ysqr = y * y

    # pitch (x-axis rotation)

    t0 = 2.0 * (w * x + y * z)

    t1 = 1.0 - 2.0 * (x * x + ysqr)

    # print('t0:{}, t1:{}'.format(t0, t1))

    pitch = math.atan2(t0, t1)

    # yaw (y-axis rotation)

    t2 = 2.0 * (w * y - z * x)

    if t2 > 1.0:
        t2 = 1.0

    if t2 < -1.0:
        t2 = -1.0

    yaw = math.asin(t2)

    # roll (z-axis rotation)

    t3 = 2.0 * (w * z + x * y)

    t4 = 1.0 - 2.0 * (ysqr + z * z)

    roll = math.atan2(t3, t4)

    print('pitch:{}, yaw:{}, roll:{}'.format(pitch, yaw, roll))

    # 单位转换：将弧度转换为度

    pitch_degree = int((pitch / math.pi) * 180)

    yaw_degree = int((yaw / math.pi) * 180)

    roll_degree = int((roll / math.pi) * 180)

    return 0, pitch, yaw, roll, pitch_degree, yaw_degree, roll_degree


def get_pose_estimation_in_euler_angle(landmark_shape, im_szie):
    try:

        ret, image_points = get_image_points_from_landmark_shape(landmark_shape)

        if ret != 0:
            print('get_image_points failed')

            return -1, None, None, None

        ret, rotation_vector, translation_vector, camera_matrix, dist_coeffs = get_pose_estimation(im_szie,
                                                                                                   image_points)

        if ret != True:
            print('get_pose_estimation failed')

            return -1, None, None, None

        ret, pitch, yaw, roll = get_euler_angle(rotation_vector)

        if ret != 0:
            print('get_euler_angle failed')

            return -1, None, None, None

        euler_angle_str = 'Pitch:{}, Yaw:{}, Roll:{}'.format(pitch, yaw, roll)

        print(euler_angle_str)

        return 0, pitch, yaw, roll



    except Exception as e:

        print('get_pose_estimation_in_euler_angle exception:{}'.format(e))

        return -1, None, None, None
def detectHead(imagePath):
    im = cv2.imread(imagePath)
    size = im.shape
    if size[0] > 700:
        h = size[0] / 3

        w = size[1] / 3

        im = cv2.resize(im, (int(w), int(h)), interpolation=cv2.INTER_CUBIC)

        size = im.shape

    ret, image_points = get_image_points(im)

    if ret != 0:
        print('get_image_points failed')
        return None
    ret, rotation_vector, translation_vector, camera_matrix, dist_coeffs = get_pose_estimation(size, image_points)

    if ret != True:
        print('get_pose_estimation failed')
        return None

    ret, pitch, yaw, roll, pitch_degree, yaw_degree, roll_degree = get_euler_angle(rotation_vector)

    draw = im.copy()

    # Yaw:

    if yaw_degree < 0:
        output_yaw = "face turns left:" + str(abs(yaw_degree)) + " degrees"

        # cv2.putText(draw,output_yaw,(20,40),cv2.FONT_HERSHEY_SIMPLEX,.5,(0,255,0))

        print(output_yaw)

    if yaw_degree == 0:
        print("face doesn't turns left or right")

    if yaw_degree > 0:
        output_yaw = "face turns right:" + str(abs(yaw_degree)) + " degrees"

        # cv2.putText(draw,output_yaw,(20,40),cv2.FONT_HERSHEY_SIMPLEX,.5,(0,255,0))

        print(output_yaw)

    # Pitch:

    if pitch_degree > 0:
        output_pitch = "face downwards:" + str(abs(pitch_degree)) + " degrees"

        # cv2.putText(draw,output_pitch,(20,80),cv2.FONT_HERSHEY_SIMPLEX,.5,(0,255,0))

        print(output_pitch)

    if pitch_degree == 0:
        print("face not downwards or upwards")

    if pitch_degree < 0:
        output_pitch = "face upwards:" + str(abs(pitch_degree)) + " degrees"

        # cv2.putText(draw,output_pitch,(20,80),cv2.FONT_HERSHEY_SIMPLEX,.5,(0,255,0))

        print(output_pitch)

    # Roll:

    if roll_degree < 0:
        output_roll = "face bends to the right:" + str(abs(roll_degree)) + " degrees"

        # cv2.putText(draw,output_roll,(20,120),cv2.FONT_HERSHEY_SIMPLEX,.5,(0,255,0))

        print(output_roll)

    if roll_degree == 0:
        print("face doesn't bend to the right or the left.")

    if roll_degree > 0:
        output_roll = "face bends to the left:" + str(abs(roll_degree)) + " degrees"

        # cv2.putText(draw,output_roll,(20,120),cv2.FONT_HERSHEY_SIMPLEX,.5,(0,255,0))

        print(output_roll)

    # Initial status:

    if abs(yaw) < 0.00001 and abs(pitch) < 0.00001 and abs(roll) < 0.00001:
        # cv2.putText(draw,"Initial ststus",(20,40),cv2.FONT_HERSHEY_SIMPLEX,.5,(0,255,0))

        print("Initial ststus")

    # 欧拉角度
    return pitch_degree,yaw_degree,roll_degree

if __name__ == '__main__':

    # Read Image
    imagePath = "E:/aut/RD/project/attentiveness-detection-master/test_datasets/10.jpg"
    detectHead(imagePath)

