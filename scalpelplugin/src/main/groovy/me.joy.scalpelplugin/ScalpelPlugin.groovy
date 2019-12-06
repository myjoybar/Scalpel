package me.joy.scalpelplugin

import com.android.build.gradle.BaseExtension
import me.joy.scalpelplugin.costtime.CostTimeTransform
import me.joy.scalpelplugin.extention.ConfigHelper
import me.joy.scalpelplugin.extention.ScalpelExtension
import me.joy.scalpelplugin.logger.AutoLoggerTransform
import me.joy.scalpelplugin.utils.L
import me.joy.scalpelplugin.viewclick.ViewClickTransform
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
        registerAutoLoggerTransform(baseExtension)
        registerViewClickTransform(baseExtension)
        registerCostTimeTransformTransform(baseExtension)


        project.afterEvaluate {
            L.print("ScalpelExtension.enable :  " + scalpelExtension.isEnable());
            L.print("ScalpelExtension.enableLog :  " + scalpelExtension.isEnableLog());
            L.print("ScalpelExtension.isMethodMethodTrace :  " + scalpelExtension.isEnableMethodTrace());
            L.print("ScalpelExtension.enableViewClickTrace :  " + scalpelExtension.isEnableViewClickTrace());
            ConfigHelper.instance.setScalpelExtension(scalpelExtension);

        }
    }

    private void registerAutoLoggerTransform(BaseExtension baseExtension ){
         baseExtension.registerTransform(new AutoLoggerTransform())
    }

    private void registerViewClickTransform(BaseExtension baseExtension ){
        baseExtension.registerTransform(new ViewClickTransform())
    }

    private void registerCostTimeTransformTransform(BaseExtension baseExtension ){
        baseExtension.registerTransform(new CostTimeTransform())
    }
}

