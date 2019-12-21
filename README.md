# rabbit-delay-queue
sample: delay queue with rabbit<br>

##1、作为一个spring gradle multiple module模板<br>
gradle多项目

##2、producer两种方式的延迟队列
<li>1).rabbitmq_delayed_message_exchange插件</li>
<li>2).消息TTL+死信队列</li>

##3、使用统一的消息格式，spring配置模板:
<li>XMessage</li>
<li>requeueIfFail:由spring的异常控制消息重发</li>
<li>配置客户端重试</li>

