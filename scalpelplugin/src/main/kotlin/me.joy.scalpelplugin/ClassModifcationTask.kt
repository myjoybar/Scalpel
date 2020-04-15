//package me.joy.scalpelplugin
//
//import org.gradle.api.DefaultTask
//import org.gradle.api.tasks.TaskAction
//
///**
// * Created by Seal.Wu on 2019-11-11
// * Desc:　TalkU马甲包混淆处理任务　
// */
//open class TalkUVestObfuscateTask : DefaultTask(){
//
//    init {
//        group = "ClassModification"
//    }
//    /**
//     * 执行TalkU马甲包的马甲包混淆处理
//     */
//    @TaskAction
//    fun doObfuscate() {
//        val rootProjectDirPath = project.rootProject.rootDir.absolutePath
//        ClassModifier().execute(projectPath = rootProjectDirPath)
//    }
//}