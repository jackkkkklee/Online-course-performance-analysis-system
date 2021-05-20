import math
import numpy as np
import os
import dlib
import cv2



class Lips(object):
    """
    This class creates a new frame to isolate the eye and
    initiates the pupil detection.
    """
    MOUTH_CORNER_LEFT = [48 , 60]
    MOUTH_CORNER_RIGHT = [54,64]
    LIPS_LINE = [61,62 ,63]
    EVALUATION = 6

    def __init__(self,  landmarks,frame):
        # 正 嘴角上扬 ；负 嘴角下移    一侧嘴角上移
        self.different=None
        self.result = None
        self._analyze( landmarks,frame)
    #
    def __init__(self, landmarks):
        # 正 嘴角上扬 ；负 嘴角下移    一侧嘴角上移
        self.different = None
        self.result = None
        self._analyze(landmarks)

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


    def _analyze(self, landmarks,frame):


        points = self.MOUTH_CORNER_LEFT
        left_corner_point1 = (landmarks.part(points[0]).x, landmarks.part(points[0]).y)
        left_corner_point2 = (landmarks.part(points[1]).x, landmarks.part(points[1]).y)

        points = self.MOUTH_CORNER_RIGHT
        right_corner_point1 = (landmarks.part(points[0]).x, landmarks.part(points[0]).y)
        right_corner_point2 = (landmarks.part(points[1]).x, landmarks.part(points[1]).y)


        points = self.LIPS_LINE
        lips_point1 = (landmarks.part(points[0]).x, landmarks.part(points[0]).y)
        lips_point2 = (landmarks.part(points[1]).x, landmarks.part(points[1]).y)
        lips_point3 = (landmarks.part(points[2]).x, landmarks.part(points[2]).y)

        left_corner_line = (left_corner_point1[1]+left_corner_point2[1])/2
        right_corner_line = (right_corner_point1[1] + right_corner_point2[1]) / 2
        lips_line = (lips_point1[1]+lips_point2[1]+lips_point3[1])/3

        # 图像坐标原点在左上角
        if left_corner_line >= lips_line and right_corner_line >= lips_line:
            # 嘴角下移
            self.different = lips_line-(left_corner_line+right_corner_line)/2
            if abs(self.different)>self.EVALUATION:
                self.result = "DOWN"
            else:
                self.result = "NATURE"
        if left_corner_line < lips_line and right_corner_line < lips_line:
            # 嘴角上扬
            self.different = lips_line-(left_corner_line+right_corner_line)/2
            if abs(self.different) > self.EVALUATION:
                self.result = "UP"
            else:
                self.result = "NATURE"
        if  (left_corner_line < lips_line and right_corner_line >= lips_line) or (left_corner_line <= lips_line and right_corner_line > lips_line):
            self.result = "OneSide"
        print( self.different)
        print(self.result)
        font = cv2.FONT_HERSHEY_SIMPLEX  # 定义字体
        imgzi = cv2.putText(frame, '0', left_corner_point1, font, 1.2, (255, 255, 255), 2)
        imgzi = cv2.putText(frame, '0', left_corner_point2, font, 1.2, (255, 255, 255), 2)
        imgzi = cv2.putText(frame, '0', right_corner_point1, font, 1.2, (255, 255, 255), 2)
        imgzi = cv2.putText(frame, '0', right_corner_point2, font, 1.2, (255, 255, 255), 2)
        imgzi = cv2.putText(frame, '0', lips_point1, font, 1.2, (255, 255, 255), 2)
        imgzi = cv2.putText(frame, '0', lips_point2, font, 1.2, (255, 255, 255), 2)
        imgzi = cv2.putText(frame, '0', lips_point3, font, 1.2, (255, 255, 255), 2)
        # 图像，文字内容， 坐标 ，字体，大小，颜色，字体厚度
        cv2.imshow('putText', imgzi)  # 显示添加文字操作的图像
        cv2.waitKey()
    def _analyze(self, landmarks):


        points = self.MOUTH_CORNER_LEFT
        left_corner_point1 = (landmarks.part(points[0]).x, landmarks.part(points[0]).y)
        left_corner_point2 = (landmarks.part(points[1]).x, landmarks.part(points[1]).y)

        points = self.MOUTH_CORNER_RIGHT
        right_corner_point1 = (landmarks.part(points[0]).x, landmarks.part(points[0]).y)
        right_corner_point2 = (landmarks.part(points[1]).x, landmarks.part(points[1]).y)


        points = self.LIPS_LINE
        lips_point1 = (landmarks.part(points[0]).x, landmarks.part(points[0]).y)
        lips_point2 = (landmarks.part(points[1]).x, landmarks.part(points[1]).y)
        lips_point3 = (landmarks.part(points[2]).x, landmarks.part(points[2]).y)

        left_corner_line = (left_corner_point1[1]+left_corner_point2[1])/2
        right_corner_line = (right_corner_point1[1] + right_corner_point2[1]) / 2
        lips_line = (lips_point1[1]+lips_point2[1]+lips_point3[1])/3

        # 图像坐标原点在左上角
        if left_corner_line >= lips_line and right_corner_line >= lips_line:
            # 嘴角下移
            self.different = lips_line-(left_corner_line+right_corner_line)/2
            if abs(self.different)>self.EVALUATION:
                self.result = "DOWN"
            else:
                self.result = "NATURE"
        if left_corner_line < lips_line and right_corner_line < lips_line:
            # 嘴角上扬
            self.different = lips_line-(left_corner_line+right_corner_line)/2
            if abs(self.different) > self.EVALUATION:
                self.result = "UP"
            else:
                self.result = "NATURE"
        if  (left_corner_line < lips_line and right_corner_line >= lips_line) or (left_corner_line >= lips_line and right_corner_line < lips_line):
            self.result = "OneSide"
        print( self.different)
        print(self.result)
        print("进来了")


def getLandmarks(image_path):
    cwd = os.path.abspath(os.path.dirname(__file__))
    model_path = os.path.abspath(os.path.join(cwd,
                                              "D:/attentiveness-detection-master/gaze_tracking/trained_models/shape_predictor_68_face_landmarks.dat"))
    predictor = dlib.shape_predictor(model_path)
    face_detector = dlib.get_frontal_face_detector()
    frame = cv2.imread(image_path)
    faces = face_detector(frame)
    if len(faces) ==0 :
        landmarks = None
    else:
        landmarks = predictor(frame, faces[0])

    return  landmarks,frame


if __name__ == "__main__":
    cwd = os.path.abspath(os.path.dirname(__file__))
    model_path = os.path.abspath(os.path.join(cwd, "E:/aut/RD/project/attentiveness-detection-master/gaze_tracking/trained_models/shape_predictor_68_face_landmarks.dat"))
    predictor = dlib.shape_predictor(model_path)
    face_detector = dlib.get_frontal_face_detector()
    frame = cv2.imread("E:/aut/RD/project/attentiveness-detection-master/test_datasets/111.jpg")
    faces = face_detector(frame)
    landmarks = predictor(frame, faces[0])

    lips = Lips(landmarks)


