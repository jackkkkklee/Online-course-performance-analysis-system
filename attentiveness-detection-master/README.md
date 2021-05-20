## Attentiveness
  1) Eye Gaze - Direction in which user is looking i.e. center / right / left ; from center of the screen
  2) Forehead Position - Position of user's forehead i.e. up / down / center ; from center of the screen
  3) Lips Distance - User is speaking / silent / yawning
    
  Based on above 3 estimations following things are done : attentiveness is scored, tells whether the user is feeling sleepy or not



需要注意的是：
1. sleep的检测机制（通过分别计算左右眼睛上下开合点的距离，除以鼻子到前额的距离 作为一个特征来判断是否犯困。在犯困的基础上，看头是否朝下60帧）
2. 打哈欠机制（嘴巴上下扩大到一定程度就是打哈欠）
3. 注意力分析机制（基础注意力，流畅注意力和平均注意力）
    面部朝向不为中，眼神不看中间，注意力都为0
    
    可以调用百度APIhttps://ai.baidu.com/ai-doc/BODY/6k3cpymz1
    检测是否吸烟或者玩手机
    
    由于智能设置听课专注度，所以写作业等环节需要避开。可能需要平台控制
    
Basic algorithm reference from jainamshah17.