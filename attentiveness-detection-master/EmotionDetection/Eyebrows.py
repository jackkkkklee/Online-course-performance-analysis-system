import math
import numpy as np
import os
import dlib
import cv2



class Eyebrows(object):
    """
    This class creates a new frame to isolate the eye and
    initiates the pupil detection.
    """

    LEFT_EYE_POINTS = [36, 37, 38, 39, 40, 41]
    RIGHT_EYE_POINTS = [42, 43, 44, 45, 46, 47]
    LEFT_EYEBROW = [18, 21]
    RIGHT_EYEBROW = [22 , 25]


    def __init__(self,  landmarks):
        self.left_degree=None
        self.right_degree = None



        self._analyze( landmarks)

    @staticmethod
    def _middle_point(nparray):
        x_sum=0
        y_sum=0
        length = len(nparray)
        for point in nparray:
            x_sum += int(point[0])
            y_sum += int(point[1])
        x = x_sum/length
        y = y_sum/length
        return (x, y)


    def _analyze(self, landmarks):
        points = self.LEFT_EYEBROW
        left_point1 = (landmarks.part(points[0]).x, landmarks.part(points[0]).y)
        left_point2 = (landmarks.part(points[1]).x, landmarks.part(points[1]).y)
        points = self.RIGHT_EYEBROW
        right_point1 = (landmarks.part(points[0]).x, landmarks.part(points[0]).y)
        right_point2 = (landmarks.part(points[1]).x, landmarks.part(points[1]).y)

        left_eye_points= self.LEFT_EYE_POINTS
        region = np.array([(landmarks.part(point).x, landmarks.part(point).y) for point in left_eye_points])
        region = region.astype(np.int32)
        left_eye_center = self._middle_point(region)

        right_eye_points = self.RIGHT_EYE_POINTS
        region = np.array([(landmarks.part(point).x, landmarks.part(point).y) for point in right_eye_points])
        region = region.astype(np.int32)

        right_eye_center = self._middle_point(region)
        print(region)
        self.left_degree = self.cal_ang(left_point1,left_eye_center,left_point2)
        self.right_degree = self.cal_ang(right_point1 , right_eye_center , right_point2)
        print( self.left_degree )
        print( self.right_degree)

    def cal_ang(self,point_1, point_2, point_3):

        """

        根据三点坐标计算夹角

        :param point_1: 点1坐标

        :param point_2: 点2坐标

        :param point_3: 点3坐标

        :return: 返回任意角的夹角值，这里只是返回点2的夹角

        """

        a = math.sqrt((point_2[0] - point_3[0]) * (point_2[0] - point_3[0]) + (point_2[1] - point_3[1]) * (
                    point_2[1] - point_3[1]))

        b = math.sqrt((point_1[0] - point_3[0]) * (point_1[0] - point_3[0]) + (point_1[1] - point_3[1]) * (
                    point_1[1] - point_3[1]))

        c = math.sqrt((point_1[0] - point_2[0]) * (point_1[0] - point_2[0]) + (point_1[1] - point_2[1]) * (
                    point_1[1] - point_2[1]))

        A = math.degrees(math.acos((a * a - b * b - c * c) / (-2 * b * c)))

        B = math.degrees(math.acos((b * b - a * a - c * c) / (-2 * a * c)))

        C = math.degrees(math.acos((c * c - a * a - b * b) / (-2 * a * b)))

        return B

if __name__ == "__main__":
    cwd = os.path.abspath(os.path.dirname(__file__))
    model_path = os.path.abspath(os.path.join(cwd, "E:/aut/RD/project/attentiveness-detection-master/gaze_tracking/trained_models/shape_predictor_68_face_landmarks.dat"))
    predictor = dlib.shape_predictor(model_path)
    face_detector = dlib.get_frontal_face_detector()
    frame = cv2.imread("E:/aut/RD/project/attentiveness-detection-master/2.jpg")
    faces=face_detector(frame)
    landmarks = predictor(frame, faces[0])

    eye = Eyebrows(landmarks)
