# LockRecommend 6.6晚 
## 一、 不同锁机制单机测试
### 主要内容
当前主要针对 ReadWriteLock,ReentrantLock,StampedLock,Synchronized四种类型的锁机制进行了单机测试，影响属性选取不多，结果也只对执行时间进行了记录。主要对线程数，读线程数，执行次数，数据结构等因素进行实验。测试中的读写操作目前仅对数字类型，具体操作也仅是简单的读取与插入。得到约15000条数据，存入resource文件夹下lockresults.xls文件。

锁测试基础数据主要测试：
数据结构为 ArrayList,HashMap,TreeSet
线程数为 5,50,80,100,200,500,800,1000,1200,1500,1800,2400,2800,3000,3300,3500,3800,4000,4200,4500,4700,5000,6000,7000,8000,10000
读线程数为 0,1,5,50,100,1000,2000,3000,4000,5000,6000,7000,8000,9000,10000
执行次数为 5,50,100,1000,10000,100000
### 类说明
#### lock包：单机测试不同情况下的执行时间
Constant.java:基础常量的定义
DataBasic.java:初始化数据结构的数据
LockTypePre.java:反射机制获得锁机制的操作对象+SetClass.java
SaveToExcel.java:将实验结果保存到文件
TestInfo.java:测试信息类
ThreadStart.java:启动单次测试，打印结果并调用SaveToExcel中保存结果
#### lock.locktype包:根据不同数据结构定义不同锁机制操作类
#### lock.thread包:读写执行类

## 二、案例设计与推荐
### 主要内容
计划根据已有的测试数据，分析各个因素的影响权重，得到对锁机制影响的因素构成，最终得到推荐案例库。
由于目前尚未找到合适方法，具体权重值以及案例库文件（resource/caseresults.xls）均为人为设置
目前案例检索相似度计算上只是采用简单方法计算两个数值间的距离。计划采用聚类方法先将案例库分类，使用更加准确的相似度计算方法。
具体过程：
1、分析数据，建立案例库（未完成）
2、将给出的案例与案例库中的所有案例进行一一对比，计算各个属性的相似度，最终得到相似度排名前几的案例及其相似度,并给出推荐。

### 类说明
#### cbr包:案例分析，案例检索，案例推荐
Case.java:案例类包含基本信息
CaseBasicMethod.java:定义基本的方法:初始化案例库等
#### cbr.create包:分析一中的结果，生成案例库（未完成）
#### cbr.recommend包:完成推荐

## 三、测试
### lock.locktest包:
### lock.cbrtest包:
![](https://github.com/dscpro/lockTest/blob/master/src/main/resource/%E6%8E%A8%E8%8D%90%E7%BB%93%E6%9E%9C.png)  
![](https://github.com/dscpro/lockTest/blob/master/src/main/resource/%E8%BF%90%E8%A1%8C%E7%BB%93%E6%9E%9C.png) 
