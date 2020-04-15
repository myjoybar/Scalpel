//package me.joy.scalpelplugin
//
//import org.dom4j.io.SAXReader
//import java.io.File
//
///**
// * Created by Joy on 2020-04-10
// */
//class ClassModifier {
//
//    fun execute(projectPath: String = "/Users/apple/StudioProjects/dingtone/DingtoneAndroid") {
//
//        val projectDir = File(projectPath)
//        replaceActivityName(projectDir)
//        replacePageName(projectDir)
//        renameDir(projectPath)
//        dealCompileError(projectDir)
//       // modifyBuildSHFile(projectDir)
//    }
//
////    private fun modifyBuildSHFile(projectDir: File) {
////        val buildSHFile = File(projectDir, "dtbuild.sh")
////        buildSHFile.writeText(buildSHFile.readText(charset("utf-8")).replace("me/dingtone/app/im","me/talktone/app/im"))
////    }
//
//
//    private fun replaceActivityName(projectDir: File) {
//        val tobeRenameActivityNames = obtainActivityNames(projectDir)
//
//        // check obfuscate path
//        val obfuscatePath = File(projectDir, "dingtoneApp/build/outputs/obfuscate/")
//        if (!obfuscatePath.exists()) {
//            obfuscatePath.mkdirs()
//        }
//        // clean obfuscate file
//        File(obfuscatePath, "obfuscate.txt").writeText("")
//
//        val activityNamesMap = tobeRenameActivityNames.mapIndexed { index, activityName ->
//
//            // write to obfuscate file
//            File(obfuscatePath, "obfuscate.txt").appendText("$activityName --> A$index\n")
//
//            Pair(activityName, "A$index")
//        }
//
//        val activityRelatedSourceDirs = listOf(File(projectDir, "dingtone_lib/src"), File(projectDir, "dingtoneApp/src"))
//        activityRelatedSourceDirs.forEach {
//            replaceClassNameInDirRecursively(it, activityNamesMap)
//            renameFileName (it, activityNamesMap)
//        }
//
//    }
//
//    private fun replaceClassNameInDirRecursively(dir: File, replaceRule: Iterable<Pair<String, String>>) {
//        dir.listFiles()?.forEach { file ->
//            if (file.isDirectory) {
//                replaceClassNameInDirRecursively(file, replaceRule)
//            } else {
//                if (file.name.substringAfterLast(".") in listOf("java", "kt", "gradle", "txt", "pro", "xml")) {
//                    var fileText = file.readText(charset("utf-8"))
//                    replaceRule.forEach { rule ->
//                        val regex = Regex("([^A-Za-z0-9_\"])${rule.first}([^A-Za-z0-9_])")
//                        fileText = fileText.replace(regex, "$1${rule.second}$2")
//                    }
//                    file.writeText(fileText)
//                }
//            }
//        }
//    }
//
//
//    private fun renameFileName(dir: File, renameRules: List<Pair<String, String>>) {
//        dir.listFiles()?.forEach { file ->
//            if (file.isDirectory) {
//                renameFileName(file, renameRules)
//            } else {
//                renameRules.forEach { rule ->
//                    if (file.name.substringBeforeLast(".") == rule.first) {
//                        file.renameTo(File(file.absolutePath.replace(rule.first, rule.second)))
//                    }
//                }
//            }
//        }
//    }
//
//
//    private fun obtainActivityNames(projectDir: File): List<String> {
//        val dingtoneAppManifestFile = File(projectDir, "dingtoneApp/src/main/AndroidManifest.xml")
//        val doc = SAXReader().read(dingtoneAppManifestFile)
//        val activityElements = doc.rootElement.element("application").elements("activity")
//        return activityElements.filter {
//            it.attributeValue("name").startsWith("me.dingtone.app.im")
//        }.map { it.attributeValue("name").substringAfterLast(".") }
//    }
//
//
//    private fun replacePageName(projectDir: File) {
//        //replace me.dingtone.app.im -> me.talktone.app.im，com.dingtone.adlibrary -> com.talktone.adlibrary
//        val replaceRule = listOf(Pair("com.dingtone.adlibrary", "com.talktone.adlibrary"), Pair("me.dingtone.app.im", "me.talktone.app.im"))
//
//        val adlibraryProjectDir = File(projectDir, "adlibrary")
//        val dingtone_libProjectDir = File(projectDir, "dingtone_lib")
//        val dt_libProjectDir = File(projectDir, "dt_lib")
//        val dingtoneAppProjectDir = File(projectDir, "dingtoneApp")
//
//        replaceTextInDirRecursively(dingtone_libProjectDir, replaceRule)
//        replaceTextInDirRecursively(adlibraryProjectDir, replaceRule)
//        replaceTextInDirRecursively(dt_libProjectDir, replaceRule)
//        replaceTextInDirRecursively(dingtoneAppProjectDir, replaceRule)
//
//    }
//
//    private fun replaceTextInDirRecursively(dir: File, replaceRule: Iterable<Pair<String, String>>) {
//        dir.listFiles()?.forEach { file ->
//            if (file.isDirectory) {
//                replaceTextInDirRecursively(file, replaceRule)
//            } else {
//                if (file.name.substringAfterLast(".") in listOf("java", "kt", "gradle", "txt", "pro", "xml")) {
//                    var fileText = file.readText(charset("utf-8"))
//                    replaceRule.forEach { rule ->
//                        fileText = fileText.replace(rule.first, rule.second)
//                    }
//                    file.writeText(fileText)
//                }
//            }
//        }
//    }
//
//
//    /**
//     * 把实际的包路径对应的文件夹改名
//     * 所以包路径中的dingtone->talktone
//     */
//    private fun renameDir(projectPath: String) {
//        val tobeRenameDirRelatedPaths = listOf<String>(
//                "adlibrary/src/main/java/com/dingtone",
//                "dingtone_lib/src/main/java/me/dingtone",
//                "dt_lib/src/main/java/me/dingtone",
//                "dingtone_lib/src/talku/java/me/dingtone")
//
//        tobeRenameDirRelatedPaths.forEach {
//            val newDir = File(projectPath, it.replace("/dingtone", "/talktone"))
//            if (newDir.exists()) {
//                newDir.deleteRecursively()
//            }
//            File(projectPath, it).renameTo(newDir)
//        }
//    }
//
//
//    private fun dealCompileError(projectDir: File) {
//        val javaFileMP3Recorder = File(projectDir,"dingtone_lib/src/main/java/me/talktone/app/im/mp3recorder/MP3Recorder.java")
//        val javaFileDataEncodeThread = File(projectDir,"dingtone_lib/src/main/java/me/talktone/app/im/mp3recorder/DataEncodeThread.java")
//        javaFileMP3Recorder.writeText(javaFileMP3Recorder.readText(charset("utf-8")).replace("me.talktone.app.im.mp3recorder.LameUtil","me.dingtone.app.im.mp3recorder.LameUtil"))
//        javaFileDataEncodeThread.writeText(javaFileDataEncodeThread.readText(charset("utf-8")).replace("me.talktone.app.im.mp3recorder.LameUtil","me.dingtone.app.im.mp3recorder.LameUtil"))
//    }
//
//
//
//}