# VI、Java、Ant和Junit的自学报告
### 学习VI编辑器的使用
此次我将使用VI编辑器编写一个简单的Java程序。
进入编辑器后输入i来编写代码，之后键入‘Esc’和‘：wq’来保存退出，过程截图如下：
![这里写图片描述](https://img-blog.csdn.net/20180414111618221?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNjMwODQ0NQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 学习Java语言，完成HelloWorld的编译运行
我用上面VI编辑器编辑的Hello.java程序进行编译运行，没有出现问题，结果如下图：
![这里写图片描述](https://img-blog.csdn.net/20180414112139682?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNjMwODQ0NQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

### 熟悉Ant环境并学习Ant，利用Ant实现HelloWorld的自动编译
云平台已经配置好了Ant环境，因此我便跳过Ant环境配置这一步。
下面时我编写的一段xml代码，用来自动编译Hello.java：
```
<?xml version="1.0"?>
<project name="Hello" default="run" basedir=".">
	<target name="clean">
		<delete dir="build" />
	</target>

	<target name="compile" depends="clean">
		<mkdir dir="build/classes" />
		<javac srcdir="src" destdir="build/classes" />
	</target>

	<target name="run" depends="compile">
		<java classname="Hello">
			<classpath>
				<pathelement path="build/classes" />
			</classpath>
		</java>
	</target>
</project>
```

<project>标签中定义了一个<target>标签，该标签表示的就是一个任务，<target>标签中的name属性表示任务名。depends属性则表示在完成“run”任务前要先完成“compile”的任务，而在compile任务完成前要先完成clean。
clean用来清除build目录下的文件，compile将编译java文件生成的class文件放入build/classes文件夹中，run则运行生成的class文件。
在终端中键入ant后结果显示编译成功,输出了“Hello World!”。

### 学习Junit，利用Ant和Junit测试通过HelloWorld
TestHelloWorld代码：
```
import static org.junit.Assert.*;
import org.junit.Test;

public class TestHelloWorld{
	public HelloWorld helloworld = new HelloWorld();

	@Test
	public void testHello(){
		helloworld.hello();
		assertEquals("Hello World!",helloworld.getStr());
	}
}
```

build.xml文件代码：
```
<?xml version="1.0"?>  
<project name="ant_and_junit" default="junit" basedir=".">  

    <property name="lib.dir" value="lib" />
    <path id="classpath">
        <fileset dir="${lib.dir}" includes="**/*.jar" />
    </path>

    <target name="clean">
    	<delete dir="build" />
    </target>

    <target name="compile" depends="clean">
    	<mkdir dir="build/classes" />
    	<javac srcdir="src" destdir="build/classes" classpathref="classpath" />
    </target>

    <!--<target name="run" depends="compile">
    	<java classname="Hello">
    		<classpath>
    			<pathelement path="build/classes" />
    		</classpath>
    	</java>
    </target>-->

    <target name="test_compile" depends="clean">
        <javac srcdir="test" destdir="build/classes" classpathref="classpath" />
    </target>

    <target name="all_compile" depends="compile, test_compile" />

    <target name="junit" depends="all_compile">
        <junit printsummary="on" fork="true" showoutput="true">
            <classpath>
                <fileset dir="${lib.dir}" includes="**/*.jar"/>
                <pathelement path="build/classes" />
            </classpath>
            <formatter type="xml" />
            <batchtest todir="build">
                <fileset dir="build/classes">
                    <include name="**/Test*.*"/>
                </fileset>
            </batchtest>
        </junit>
        <junitreport todir="build">
            <fileset dir="build">
                <include name="Test-*.xml"/>
            </fileset>
            <report format="frames" todir="build"/>
        </junitreport>
    </target>

</project>
```

成功通过了测试，测试结果如图：
![这里写图片描述](https://img-blog.csdn.net/20180414161853521?watermark/2/text/aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl8zNjMwODQ0NQ==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70)

