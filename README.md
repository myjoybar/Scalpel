# Scalpel



## Features

- Modify PackageName & ClassName
- Add garbage code

## Installation
- 运行ScalpelPlugin下的uploadArchives脚本，在项目跟路径下会生成repo文件夹，拷贝至你的项目，参照demo示例，配置Gradle

## Modify PackageName & ClassName
- CMD
  
   - `gradle vestTask`
- Config
		
	```groovy
	 def modules = ['app'] // module name
	 VestConfig {
	    vestModules modules
	}
	```
- 映射文件
	- 在module下会生成映射文件，如下
	
	```java
	.main.java.su.taskp.sdk.testmodule.AppTestActivity.java  -->  .main.java.mhba.imxb.pwmq.zuij.impzActivity.java
	.main.java.su.taskp.sdk.testmodule.AppTestActivity.kt  -->  .main.java.mhba.imxb.pwmq.zuij.knewActivity.kt
	.main.java.su.taskp.sdk.testmodule.OrderActivity.kt  -->  .main.java.mhba.imxb.pwmq.zuij.xzvaActivity.kt
	.main.java.su.taskp.sdk.testmodule.TestActivity.kt  -->  .main.java.mhba.imxb.pwmq.zuij.asvvActivity.kt
	.main.java.su.taskp.sdk.testmodule.TestApplication.kt  -->  .main.java.mhba.imxb.pwmq.zuij.tikbApplication.kt
	.main.java.su.taskp.sdk.testmodule.TestTrackerService.kt  -->  .main.java.mhba.imxb.pwmq.zuij.nkzl.kt
	```

- 注意事项
	1. 代码一定要code format
	2. 类名文件名不要和第三方库重复
	3. 禁止import使用通配符* 请按照这个[ Link ](https://stackoverflow.com/questions/49870306/disable-wild-cart-import-in-intellij-android-studio-in-kotlin)设置	

## Garbage code

- Config
	
	```groovy
	
	ScalpelConfig {
	    enable true
	    GarbageConfig {
	        enableGarbageCode true
	        handleInnerClass false  // 禁止在内部类插入
	        garbageCodeInjectRatio 0.1 // 垃圾代码插入灰度 [0-1]
	        garbageType 'C'  // A-J，一共10套，每套10个类，每个类32个方法
	
	    }
	}
	```
		
