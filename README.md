# hadoop
Hadoop code

һ��combiner
1������ÿһ��map task�ı������У����յ�map�����ÿһ��key��valuelist�����Կ������ֲ����ܴ���
2����Ϊ��map task�ı��ؽ����˾ֲ����ܣ��ͻ���map�˵����������������򣬼�Сshuffle���̵�����IO
3��combiner��ʵ����һ��reducer���������ʵ��reducer����������ڣ�combiner����maptask�ı���
4��combiner��ʹ��ʱ��Ҫע�⣬�������KV��������Ҫ��map��reduce����Ӧ��������ƥ��
5��Ҫע��ҵ���߼�������Ϊcombiner�ļ������Ӱ��


����hadoop�����л�����
��jdk�Դ��ıȽ����������Ӿ���ֻ���ݶ����е����ݣ�����������̳нṹ�ȶ�����Ϣ
Ҫ�����Զ��������������hadoop��Ⱥ�д��ݣ���Ҫʵ��hadoop�����л��ӿ�Writable���� WritableComparable<T>
�Զ������������beanʵ����Writable�ӿں�Ҫʵ�����е���������
public void write(DataOutput out) throws IOException   ----���л���������д���ֽ���
�Լ�
public void readFields(DataInput in) throws IOException  ----�����л������ֽ����ж�������
ע�⣺
д�����ݺͶ������ݵ�˳�������Ҫ����һ�� 

�����Զ�������
hadoop����������shuffle����ɵ�
�����������map�����key
Ҫ��ʵ���Զ�������򣬾�Ҫ����Ҫ��������ݷ�װ��key�д��䣬����Ҫ������ʵ��WritableComparable�ӿ�


�ġ��Զ������ partition
****ÿһ��reduce task���һ������ļ�
----�Զ���һ����AreaPartitioner �̳�  Partitioner �����࣬ʵ�����еķ��� int getPartition(K,V)
----��job������������ʹ���Զ����partitioner
					job.setPartitionerClass(AreaPartitioner.class)
----��job��������ָ����ҵ��reduce task����������job.setNumReduceTasks(5)������Ҫ��partitioner�еķ�����һ��


�塢shuffle����   ����  map task��������� �� reduce task֮���һ�����ݵ��Ȼ���
shuffle������Ҫ�Ĺ����Ƿ��������
map task������������ػ��棨�ڴ滺�����ʹ����ļ������з�������
��reduce task�˻�Ҫ�ٴν��й鲢����











