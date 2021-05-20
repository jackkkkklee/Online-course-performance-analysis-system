
import cv2
from gaze_tracking import GazeTracking

if __name__ == '__main__':
    temp_photo = cv2.imread("attentiveness-detection-master/2.jpg")
    print(temp_photo)
    frame = temp_photo
    gaze = GazeTracking()
    gaze.refresh(frame)
    print(gaze.landmarks)