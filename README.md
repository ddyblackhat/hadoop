# hadoop
Hadoop code

一、combiner
1、是在每一个map task的本地运行，能收到map输出的每一个key的valuelist，所以可以做局部汇总处理
2、因为在map task的本地进行了局部汇总，就会让map端的输出数据量大幅精简，减小shuffle过程的网络IO
3、combiner其实就是一个reducer组件，跟真实的reducer的区别就在于，combiner运行maptask的本地
4、combiner在使用时需要注意，输入输出KV数据类型要跟map和reduce的相应数据类型匹配
5、要注意业务逻辑不能因为combiner的加入而受影响


二、hadoop的序列化机制
跟jdk自带的比较起来，更加精简，只传递对象中的数据，而不传递如继承结构等额外信息
要想让自定义的数据类型在hadoop集群中传递，需要实现hadoop的序列化接口Writable或者 WritableComparable<T>
自定义的数据类型bean实现了Writable接口后，要实现其中的两个方法
public void write(DataOutput out) throws IOException   ----序列化，将数据写入字节流
以及
public void readFields(DataInput in) throws IOException  ----反序列化，从字节流中读出数据
注意：
写入数据和读出数据的顺序和类型要保持一致 

三、自定义排序
hadoop的排序是在shuffle中完成的
排序的依据是map输出的key
要想实现自定义的排序，就要将需要排序的数据封装到key中传输，并且要将数据实现WritableComparable接口


四、自定义分区 partition
****每一个reduce task输出一个结果文件
----自定义一个类AreaPartitioner 继承  Partitioner 抽象类，实现其中的方法 int getPartition(K,V)
----在job的描述中设置使用自定义的partitioner
					job.setPartitionerClass(AreaPartitioner.class)
----在job的描述中指定作业的reduce task并发数量，job.setNumReduceTasks(5)，数量要与partitioner中的分区数一致


五、shuffle机制   ——  map task的输出数据 到 reduce task之间的一种数据调度机制
shuffle中最重要的功能是分组和排序
map task端先输出到本地缓存（内存缓冲区和磁盘文件）进行分组排序
在reduce task端还要再次进行归并排序











