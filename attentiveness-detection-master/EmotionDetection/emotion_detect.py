import EmotionDetection.lips as lips
import EmotionDetection.head_orientation as head
import EmotionDetection.Eyebrows as eyebrows

def detect_emotion(imagePath):
    emotion_dic = {1: "listening", 2: "confusing", 3: "understanding", 4: "resist", 5: "disdain", 0: "unsure"}
    emotion = "unsure"

    #imagePath = "E:/aut/RD/project/attentiveness-detection-master/test_datasets/normal.jpg"
    landmarks, frame = lips.getLandmarks(imagePath)
    if landmarks is None:
        return emotion[4]
    lips_result = lips.Lips(landmarks)


    # eyebrows result
    eyebrows_result = eyebrows.Eyebrows(landmarks)
    left_degree = eyebrows_result.left_degree
    right_degree = eyebrows_result.right_degree
    # lips result
    different = lips_result.different
    # head result
    # maybe head turn left or right by a lot
    if head.detectHead(imagePath) is None:
        if lips_result.result == "OneSide":
            return emotion[5]
        else:
            return emotion[4]
    else:
        pitch_degree , yaw_degree, roll_degree =head.detectHead(imagePath)

    # the threshold   refers to the paper
    head_threshold=20
    lips_threshold=85
    # 头部转向条件 ->积极
    if int(str(abs(pitch_degree)))<head_threshold and int(str(abs(yaw_degree)))<head_threshold and int(str(abs(roll_degree)))<head_threshold:
        # 眉目自然
        print("******")
        print("眉目自然")
        if left_degree < lips_threshold and right_degree < lips_threshold:
            # 嘴角上扬
            print("******")
            print("嘴角上扬")
            print("")
            if lips_result.result == "UP" :
                emotion = emotion_dic[1]
            else:
                emotion = emotion_dic[3]
        # 眉目紧蹙
        elif left_degree>lips_threshold or right_degree>lips_threshold:
            print("******")
            print("眉目紧蹙")
            # 嘴角下移
            if lips_result.result == "DOWN":
                print("******")
                print("嘴角下移")
                emotion = emotion_dic[2]
            # 目前都当作疑惑
            else:
                print("******")
                print("嘴角未知或其他")
                emotion = emotion_dic[2]

    else:
        print("******")
        print("嘴角一边撅起")
        if lips_result.result == "OneSide":
            emotion = emotion_dic[5]
        else:
            emotion = emotion_dic[4]

    print("emotion:"+emotion)
    show_parameter(pitch_degree,yaw_degree,roll_degree,eyebrows_result,lips_result)

    return emotion
def show_parameter(pitch_degree,yaw_degree,roll_degree,eyebrows_result,lips_result):
    print("头部转向%.5f,%.5f,%.5f" %(pitch_degree,yaw_degree,roll_degree))
    print("眼角参数%s,   %s"%(eyebrows_result.right_degree,eyebrows_result.left_degree))
    print("嘴角参数%s"%(lips_result.result))
    print("嘴角区别%s" % (lips_result.different))



if __name__ == '__main__':
    # imagePath = "E:/aut/RD/project/attentiveness-detection-master/test_datasets/confusing/111.jpg"
    imagePath = "E:/aut/RD/project/attentiveness-detection-master/test_datasets/resist/111.jpg"
    detect_emotion(imagePath)
