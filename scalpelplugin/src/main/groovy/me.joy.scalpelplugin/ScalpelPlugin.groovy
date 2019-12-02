package me.joy.scalpelplugin

import com.android.build.gradle.BaseExtension
import me.joy.scalpelplugin.extention.ConfigHelper
import me.joy.scalpelplugin.extention.ScalpelExtension
import me.joy.scalpelplugin.logger.AutoLoggerTransform
import me.joy.scalpelplugin.utils.LogUtils
import org.gradle.api.Plugin
import org.gradle.api.Project

public class ScalpelPlugin implements Plugin<Project> {


    ScalpelExtension scalpelExtension;

    @Override
    void apply(Project project) {

//        def android = project.extensions.getByType(AppExtension)
//        android.registerTransform(new AutoLoggerTransform())

        BaseExtension baseExtension = (BaseExtension) project.getExtensions().getByName("android");
        project.extensions.create(Constant.SCALPEL_CONFIG, ScalpelExtension)
        scalpelExtension = project.extensions.findByType(ScalpelExtension.class)
           baseExtension.registerTransform(new AutoLoggerTransform());
//       baseExtension.registerTransform(new DebounceTransform());


//        def android = project.extensions.getByType(AppExtension)
//        android.registerTransform(new DebounceTransform())

        project.afterEvaluate {
            LogUtils.print("ScalpelExtension.enable :  " + scalpelExtension.isEnable());
            LogUtils.print("ScalpelExtension.enableLog :  " + scalpelExtension.isEnableLog());
            LogUtils.print("ScalpelExtension.isMethodTrack :  " + scalpelExtension.isEnableMethodTrack());
            ConfigHelper.instance.setScalpelExtension(scalpelExtension);

        }
    }

}

