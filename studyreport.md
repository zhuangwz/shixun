# VI、Java、Ant和Junit的自学报告
### VI
此次我将使用VI编辑器编写一个简单的Java程序。
进入编辑器后输入i来编写代码，之后键入‘Esc’和‘：wq’来保存退出，过程截图如下：
![这里写图片描述](https://img-blog.csdn.net/20180414111618221?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNjMwODQ0NQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### Java
我用上面VI编辑器编辑的Hello.java程序进行编译运行，没有出现问题，结果如下图：
![这里写图片描述](https://img-blog.csdn.net/20180414112139682?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNjMwODQ0NQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 熟悉Ant环境并学习Ant，利用Ant实现Hello的自动编译
云平台已经配置好了Ant环境，因此我便跳过Ant环境配置这一步。
下面时我编写的一段xml代码，用来自动编译Hello.java：
```
<?xml version="1.0"?>
<project name="Hello" default="run" basedir="">
	<target name="compile">
		<javac srcdir="" />
	</target>

	<target name="run" depends="compile">
		<java classname="HelloWorld" />
	</target>
</project>
```

<project>标签中定义了一个<target>标签，该标签表示的就是一个任务，<target>标签中的name属性表示任务名。depends属性则表示在完成“run”任务前要先完成“compile”的任务。
在终端中键入ant后结果显示编译成功。

### 学习Junit，利用Ant和Junit测试通过HelloWorld
