package me.joy.scalpelplugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.BaseExtension
import me.joy.scalpelplugin.costtime.CostTimeTransform
import me.joy.scalpelplugin.extention.ConfigHelper
import me.joy.scalpelplugin.extention.GarbageConfigExtension
import me.joy.scalpelplugin.extention.ScalpelExtension
import me.joy.scalpelplugin.extention.VestConfigExtension
import me.joy.scalpelplugin.garbagecode.GarbageCodeTransform
import me.joy.scalpelplugin.logger.AutoLoggerTransform
import me.joy.scalpelplugin.utils.L
import me.joy.scalpelplugin.vest.VestTask
import me.joy.scalpelplugin.viewclick.ViewClickTransform
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer

public class ScalpelPlugin implements Plugin<Project> {
    private static final String TAG = "ScalpelPlugin";

    ScalpelExtension scalpelExtension;
    GarbageConfigExtension garbageConfigExtension;
    VestConfigExtension vestConfigExtension;

    @Override
    void apply(Project project) {


        PluginContainer pluginContainer = project.getPlugins();
        Iterator<Plugin> iterator = pluginContainer.iterator()
        while (iterator.hasNext()) {
            Plugin plugin = (Plugin) iterator.next();
            L.print(TAG, "plugin ： " + plugin.getClass().getSimpleName());
//            if(plugin instanceof AppPlugin || plugin instanceof LibraryPlugin || plugin instanceof AndroidBasePlugin){
            if (plugin instanceof AppPlugin) {

                BaseExtension baseExtension = (BaseExtension) project.getExtensions().getByName("android");
                project.extensions.create(Constant.SCALPEL_CONFIG, ScalpelExtension)
                project.extensions.create(Constant.GARBAGE_CONFIG, GarbageConfigExtension)
                project.extensions.create(Constant.VEST_CONFIG, VestConfigExtension)
                scalpelExtension = project.extensions.findByType(ScalpelExtension.class)
                garbageConfigExtension = project.extensions.findByType(GarbageConfigExtension.class)
                vestConfigExtension = project.extensions.findByType(VestConfigExtension.class)
                registerAutoLoggerTransform(baseExtension)
                registerViewClickTransform(baseExtension)
                registerCostTimeTransformTransform(baseExtension)
                registerClassModifierTransform(baseExtension)
                project.afterEvaluate {
                    ConfigHelper.instance.setScalpelExtension(scalpelExtension);
                    ConfigHelper.instance.setGarbageConfigExtension(garbageConfigExtension);
                    ConfigHelper.instance.setVestConfigExtension(vestConfigExtension);

                }
            }
        }

        registerVestTask(project);


    }

    private void registerAutoLoggerTransform(BaseExtension baseExtension) {
        baseExtension.registerTransform(new AutoLoggerTransform())
    }

    private void registerViewClickTransform(BaseExtension baseExtension) {
        baseExtension.registerTransform(new ViewClickTransform())
    }

    private void registerCostTimeTransformTransform(BaseExtension baseExtension) {
        baseExtension.registerTransform(new CostTimeTransform())
    }

    private void registerClassModifierTransform(BaseExtension baseExtension) {
        baseExtension.registerTransform(new GarbageCodeTransform())
    }

    private void registerVestTask(Project project) {
        // gradle vestTask
       project.task('vestTask', type: VestTask)

    }
}

