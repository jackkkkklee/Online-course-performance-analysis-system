#coding=utf-8
import cv2
from datetime import datetime
from gaze_tracking import GazeTracking
import numpy
import dlib
import numpy as np

#PREDICTOR_PATH = "gaze_tracking/trained_models/shape_predictor_68_face_landmarks.dat" #  训练好的人脸数据加载路径
PREDICTOR_PATH = "/attentiveness-detection-master/gaze_tracking/trained_models/shape_predictor_68_face_landmarks.dat"
predictor = dlib.shape_predictor(PREDICTOR_PATH)  #  返回人脸关键点预测器
#cascade_path='haarcascade_frontalface_default.xml'
#cascade = cv2.CascadeClassifier(cascade_path)
detector = dlib.get_frontal_face_detector()  #   返回默认的人脸检测器（前额？）

RED = (0,0,255) # BGR

FOREHEAD = 27
L_EYE_CORNER = 39
R_EYE_CORNER = 42

NOSE_TIP = 30
NOSE_BASE = 33

L_EYE_TOP = 38
L_EYE_BOTTOM = 40

R_EYE_TOP = 42
R_EYE_BOTTOM = 47

#  表示两个脸部特征点之间的差距，距离用矩阵的范数表示
def distance(faceKeypoints, key1, key2, orientation=None):
  if orientation == 'horizontal':
    diff = faceKeypoints[key1][:,0] - faceKeypoints[key2][:,0]
  elif orientation == 'vertical':
    diff = faceKeypoints[key1][:,1] - faceKeypoints[key2][:,1]
  else:
    diff = numpy.linalg.norm(faceKeypoints[key1][:,0]-faceKeypoints[key2][:,0])  # 矩阵的范式，目的是 表示一个集合（向量），通过一种映射关系（矩阵），得到另外一个集合（另外一个向量）的这个变化过程的大小的一个度量   参考资料
    # https://xiongyiming.blog.csdn.net/article/details/81673491?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param

  # print(abs(diff))
  return abs(diff)

# H_THRESHOLD = 15
# V_THRESHOLD_DOWN = 30
# V_THRESHOLD_UP = 40
H_THRESHOLD = 0.35
V_THRESHOLD_DOWN = 0.35
V_THRESHOLD_UP = 0.7

EYE_THRESHOLD = 0.25

# 通过计算鼻子_嘴唇，鼻子_前额 距离的比值差 和控制方向的阈值进行对比 得到上下朝向，同理通过左右眼和前额判断 脸部左右  问题1：阈值的设定规则是？训练的还是调试出来的？
def check_face_direction(faceKeypoints):
  direction = 'Center'
  nose_to_lip = distance(faceKeypoints, NOSE_TIP, NOSE_BASE, 'vertical')
  nose_to_forehead = distance(faceKeypoints, NOSE_TIP, FOREHEAD, 'vertical')
  print("nose_to_lip/nose_to_forehead")
  print(nose_to_lip/nose_to_forehead)
  # if nose_to_forehead/nose_to_lip > V_THRESHOLD1:
  if nose_to_lip/nose_to_forehead < V_THRESHOLD_DOWN:
  # if nose_to_lip < V_THRESHOLD_DOWN:
      direction = 'Down'
  elif nose_to_lip/nose_to_forehead > V_THRESHOLD_UP:
  # elif nose_to_forehead < V_THRESHOLD_UP:
      direction = 'Up'

  r_eye_to_forehead = distance(faceKeypoints, FOREHEAD, R_EYE_CORNER, 'horizontal')
  l_eye_to_forehead = distance(faceKeypoints, FOREHEAD, L_EYE_CORNER, 'horizontal')
  #print('r_eye_to_forehead/l_eye_to_forehead',r_eye_to_forehead/l_eye_to_forehead)
  #print('l_eye_to_forehead/r_eye_to_forehead',l_eye_to_forehead/r_eye_to_forehead)
  #if distance(faceKeypoints, FOREHEAD, R_EYE_CORNER, 'horizontal') < H_THRESHOLD:
  if r_eye_to_forehead/l_eye_to_forehead < H_THRESHOLD:
      direction += '-Left'
  #elif distance(faceKeypoints, FOREHEAD, L_EYE_CORNER, 'horizontal') < H_THRESHOLD:
  elif l_eye_to_forehead/r_eye_to_forehead < H_THRESHOLD:
      direction += '-Right'

  return direction

# 通过分别计算左右眼睛上下开合点的距离，除以鼻子到前额的距离 作为一个特征来判断是否犯困
def check_for_sleep(faceKeypoints):
  l_eye_openness = distance(faceKeypoints, L_EYE_TOP, L_EYE_BOTTOM)
  r_eye_openness = distance(faceKeypoints, R_EYE_TOP, R_EYE_BOTTOM)
  nose_to_forehead = distance(faceKeypoints, NOSE_TIP, FOREHEAD, 'vertical')

  sleeping_potential = True if (l_eye_openness/nose_to_forehead < EYE_THRESHOLD) and (r_eye_openness/nose_to_forehead < EYE_THRESHOLD) else False
  print("***************************************")
  print("l_eye_openness/nose_to_forehead ")
  print(l_eye_openness/nose_to_forehead )
  print("(r_eye_openness/nose_to_forehead < EYE_THRESHOLD)")
  print(r_eye_openness/nose_to_forehead )
  print("sleep?")
  print(sleeping_potential)


  return sleeping_potential



def get_landmarks(im):  #  获取一个68*2的矩阵 特征点的坐标
    rects = detector(im, 1) # 基本脸部特征（上下左右）的结果对象

    if len(rects) > 1:
        return "error"
    if len(rects) == 0:
        return "error"
    #print("*************************")
    #print(np.matrix([[p.x, p.y] for p in predictor(im, rects[0]).parts()]))
    return np.matrix([[p.x, p.y] for p in predictor(im, rects[0]).parts()]) # 进行人脸检测并给出关键点的坐标


def annotate_landmarks(im, landmarks):
    im = im.copy()
    for idx, point in enumerate(landmarks):
        pos = (point[0, 0], point[0, 1])
        #cv2.putText(im, str(idx), pos,
         #           fontFace=cv2.FONT_HERSHEY_SCRIPT_SIMPLEX,
          #          fontScale=0.4,
           #         color=(0, 0, 255))
        # 参考资料:https://blog.csdn.net/xingchenbingbuyu/article/details/51116354
        cv2.circle(im, pos, 3, color=(0, 255, 255)) #  根据坐标，用cv2进行描点标记，返回更新后的图片
    return im

# top_lip 有多个关键点，返回top_lip平均值的点或者说是center点
def top_lip(landmarks):
    top_lip_pts = []
    # 从64个关键点中获取对应的点
    for i in range(50,53):
        top_lip_pts.append(landmarks[i])
    for i in range(61,64):
        top_lip_pts.append(landmarks[i])
    top_lip_all_pts = np.squeeze(np.asarray(top_lip_pts))
    top_lip_mean = np.mean(top_lip_pts, axis=0) # 压缩行，对各列求均值
    return int(top_lip_mean[:,1])

def bottom_lip(landmarks):
    bottom_lip_pts = []
    for i in range(65,68):
        bottom_lip_pts.append(landmarks[i])
    for i in range(56,59):
        bottom_lip_pts.append(landmarks[i])
    bottom_lip_all_pts = np.squeeze(np.asarray(bottom_lip_pts))
    bottom_lip_mean = np.mean(bottom_lip_pts, axis=0)
    return int(bottom_lip_mean[:,1])

def mouth_open(image, landmarks):
    #landmarks = get_landmarks(image)

    #if landmarks == "error":
    #    return image, 0

    image_with_landmarks = annotate_landmarks(image, landmarks)
    top_lip_center = top_lip(landmarks)
    bottom_lip_center = bottom_lip(landmarks)
    lip_distance = abs(top_lip_center - bottom_lip_center)
    return image_with_landmarks, lip_distance

# codes for gaze detection
def is_right(gaze):
  """Returns true if the user is looking to the right"""
  if gaze.pupils_located:
    return gaze.horizontal_ratio() <= 0.5

def is_left(gaze):
  """Returns true if the user is looking to the left"""
  if gaze.pupils_located:
    return gaze.horizontal_ratio() >= 0.85

def is_up(gaze):
  """Returns true if the user is looking to the up"""
  if gaze.pupils_located:
    return gaze.vertical_ratio() <= 0.3

def is_down(gaze):
  """Returns true if the user is looking to the down"""
  if gaze.pupils_located:
    return gaze.vertical_ratio() >= 0.7

def is_center(gaze):
  """Returns true if the user is looking to the center"""
  if gaze.pupils_located:
    return is_right(gaze) is not True and is_left(gaze) is not True

def getAttention(temp_photo):
    beta = 0.9
    frame_count = 0
    blink_counter = 0
    sleep_frames_counter = 0
    blink_th = 3

    gaze = GazeTracking()
    yawns = 0
    yawn_status = False

    smooth_attentiveness = -1

    # 对图片进行处理
    frame = temp_photo

    # 检测人脸
    face = detector(frame)
    if len(face)==0 or face is None:
        return "no face"

    # We send this frame to GazeTracking to analyze it
    gaze.refresh(frame)

    landmarks = np.matrix([[p.x, p.y] for p in gaze.landmarks.parts()])

    # print(np.matrix([[p.x, p.y] for p in landmarks.parts()]))

    frame = gaze.annotated_frame()
    text = ""
    attentiveness = 100
    if gaze.is_blinking():
        blink_counter += 1
    elif is_right(gaze) or is_left(gaze):  # or is_up(gaze) or is_down(gaze):    不看中间专注度基数就为0
        attentiveness = 0
        blink_counter = np.maximum(blink_counter - 1, 0)
    elif is_center(gaze):
        attentiveness = 100
        blink_counter = np.maximum(blink_counter - 1, 0)

    # if blink_counter >= blink_th:
    #     attentiveness = 0
    # 瞳孔定位判断，用于显示
    if gaze.pupils_located:
        if gaze.is_blinking():
            gaze_text = "blinking"
        elif is_right(gaze):
            gaze_text = "looking right"
        elif is_left(gaze):
            gaze_text = "looking left"
        elif is_center(gaze):
            gaze_text = "looking center"
        elif is_down(gaze):
            gaze_text = "looking down"
        elif is_up(gaze):
            gaze_text = "looking up"
    else:
        gaze_text = "unsure"

    # 面部朝向判断
    direction = check_face_direction(landmarks)
    if direction == 'Center':
        attentiveness = 90
    else:
        print("人脸朝向")
        print(direction)
        attentiveness = 0.5 * attentiveness  # 0.5 * attentiveness
        print("降低后的专注度")
        print(attentiveness)
        print("瞳孔方向")
        print(gaze_text)


    sleep_chance = check_for_sleep(landmarks)
    if sleep_chance:
        if direction == 'Down':
            sleep_frames_counter += 1
        else:
            sleep_frames_counter = np.maximum(sleep_frames_counter - 1, 0)
    # 如果超过60次检测疲惫并且注意力变为为0，就设置为睡觉，这里要更改
    if sleep_frames_counter > 60:
        attentiveness = 0
        cv2.putText(frame, "sleep detected", (30, 300), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 255), 2)

    # calculate smooth attentiveness score
    if smooth_attentiveness == -1:
        smooth_attentiveness = attentiveness
        average_attentivenss = attentiveness
    else:
        smooth_attentiveness = np.round((1.0 - beta) * attentiveness + beta * smooth_attentiveness, 1)
        #average_attentivenss = np.round(
        #    (1. / (frame_count)) * attentiveness + (1. - 1. / (frame_count)) * average_attentivenss, 1)

    update_dt = datetime.now()

    #print("attentiveness is {}, smooth attentiveness is {} and average attentiveness is {}" \
     #     .format(attentiveness, smooth_attentiveness, average_attentivenss))
    print(attentiveness)
    left_pupil = gaze.pupil_left_coords()
    right_pupil = gaze.pupil_right_coords()
    if gaze.pupils_located:
        v_ratio = np.round(gaze.vertical_ratio(), 2)
        h_ratio = np.round(gaze.horizontal_ratio(), 2)
    else:
        v_ratio, h_ratio = 0, 0

    image_landmarks, lip_distance = mouth_open(frame, landmarks)
    #cv2.imshow('Live Landmarks', image_landmarks)

    cv2.putText(frame, "Attentiveness: " + str(attentiveness), (15, 95), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 255), 2)
    # cv2.putText(frame, "Left pupil:  " + str(left_pupil), (30, 130), cv2.FONT_HERSHEY_DUPLEX, 0.9, (147, 58, 31), 1)
    # cv2.putText(frame, "Right pupil: " + str(right_pupil), (30, 165), cv2.FONT_HERSHEY_DUPLEX, 0.9, (147, 58, 31), 1)
    cv2.putText(frame, "Gaze direction: " + str(gaze_text), (15, 130), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 255), 2)
    cv2.putText(frame, "Head orientation: " + str(direction), (15, 165), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 255), 2)
    # cv2.putText(frame, "v & h ratios: " + str(v_ratio) + " "+ str(h_ratio), (30, 200), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 255), 2)
    # cv2.putText(frame, "Sleep counter: " + str(sleep_frames_counter), (15, 200), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 255), 2)
    cv2.putText(frame, text, (90, 60), cv2.FONT_HERSHEY_DUPLEX, 1, (0, 0, 235), 2)

    prev_yawn_status = yawn_status

    if lip_distance > 20:
        yawn_status = True
        # 可以增加一个时间控制判断
        cv2.putText(frame, "Subject is Yawning", (50, 450),
                    cv2.FONT_HERSHEY_COMPLEX, 1, (0, 0, 255), 2)

        # output_text = " Yawn Count: " + str(yawns + 1)
        # cv2.putText(frame, output_text, (50,50), cv2.FONT_HERSHEY_COMPLEX, 1,(0,255,127),2)

    else:
        yawn_status = False

    if prev_yawn_status == True and yawn_status == False:
        yawns += 1

    overlay = frame.copy()

    if smooth_attentiveness < 30:
        text_tpl = '   Low - {:04.1f}%'
        bkg_color = (0, 0, 255)  # Red
    elif smooth_attentiveness < 60:
        text_tpl = 'Medium - {:04.1f}%'
        bkg_color = (0, 255, 255)  # Yellow
    else:
        text_tpl = '  High - {:04.1f}%'
        bkg_color = (0, 255, 0)  # Green

    # noise = frame_count % 10 / 10.
    # print(noise)
    # if smooth_attentiveness + noise >= 100:
    #     noise -= 1
    # text = text_tpl.format(smooth_attentiveness + noise)

    # text += ' - {:04.1f}%'.format(attentiveness)

    #cv2.rectangle(overlay, rect_org, rect_end, bkg_color, -1);
    # cv2.putText(overlay,text,
    #   org=text_org,
    #   fontFace=font,
    #   fontScale=fontScale,
    #   color=(0,0,0),
    #   thickness=thickness,
    #   lineType=cv2.LINE_AA)

    #cv2.addWeighted(overlay, alpha, frame, 1 - alpha, 0, frame)
    #out.write(frame)

    # cv2.imshow('Yawn Detection', frame )

    #cv2.imshow("Demo", frame)
    #cv2.waitKey(0)
    #return attentiveness
    #print(str(direction))
    #print(yawn_status)
    #print(sleep_chance)
    result = str(attentiveness)+"\n"+str(direction)+"\n"+str(yawn_status)+"\n"+str(sleep_chance)
    print("ddd"+result)
    return result

"""
由于要检测打哈欠，眨眼频率，睡觉要检测固定帧数，所以检测频率还是要高
每次只返回面部朝向，瞬时专注度，是否打哈欠 
java中要补充算法：睡觉检测，
"""
def attention_analysis(dir):
    temp_photo = cv2.imread(dir)
    return getAttention(temp_photo)
def attention_analysis_noprameter():
    temp_photo = cv2.imread("attentiveness-detection-master/2.jpg")
    return getAttention(temp_photo)




if __name__ == '__main__':
    attention_analysis_noprameter()

