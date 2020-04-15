package me.joy.scalpelplugin

import com.android.build.gradle.BaseExtension
import me.joy.scalpelplugin.costtime.CostTimeTransform
import me.joy.scalpelplugin.extention.ConfigHelper
import me.joy.scalpelplugin.extention.GarbageConfigExtension
import me.joy.scalpelplugin.extention.ScalpelExtension
import me.joy.scalpelplugin.garbagecode.GarbageCodeTransform
import me.joy.scalpelplugin.logger.AutoLoggerTransform
import me.joy.scalpelplugin.viewclick.ViewClickTransform
import org.gradle.api.Plugin
import org.gradle.api.Project

public class ScalpelPluginBack implements Plugin<Project> {


    ScalpelExtension scalpelExtension;
    GarbageConfigExtension garbageConfigExtension;

    @Override
    void apply(Project project) {

        BaseExtension baseExtension = (BaseExtension) project.getExtensions().getByName("android");
        project.extensions.create(Constant.SCALPEL_CONFIG, ScalpelExtension)
        project.extensions.create(Constant.GARBAGE_CONFIG, GarbageConfigExtension)
        scalpelExtension = project.extensions.findByType(ScalpelExtension.class)
        garbageConfigExtension = project.extensions.findByType(GarbageConfigExtension.class)
        //registerAutoLoggerTransform(baseExtension)
        // registerViewClickTransform(baseExtension)
        //  registerCostTimeTransformTransform(baseExtension)
        // registerClassModifierTransform(baseExtension)

        project.afterEvaluate {

            ConfigHelper.instance.setScalpelExtension(scalpelExtension);
            ConfigHelper.instance.setGarbageConfigExtension(garbageConfigExtension);

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

    private void registerClassModifierTransform(BaseExtension baseExtension ){
        baseExtension.registerTransform(new GarbageCodeTransform())
    }
}

