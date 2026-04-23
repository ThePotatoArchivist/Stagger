package archives.tater.stagger;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.*;
import folk.sisby.kaleido.lib.quiltconfig.api.metadata.ChangeWarning.Type;
import folk.sisby.kaleido.lib.quiltconfig.api.metadata.NamingSchemes;

@DisplayNameConvention(NamingSchemes.TITLE_CASE)
@SerializedNameConvention(NamingSchemes.SNAKE_CASE)
public class StaggerConfig extends WrappedConfig {
    @Comment("Whether max poise should match max health instead of the below config and modifiers")
    @Comment("Must match between client & server")
    @ChangeWarning(value = Type.Custom, customMessage = "Must match between client & server")
    public boolean maxHealthIsMaxPoise = false;

    @ChangeWarning(Type.RequiresRestart)
    @IntegerRange(min = 0, max = 100)
    public int baseMaxPoise = 20;

    @ChangeWarning(Type.RequiresRestart)
    @FloatRange(min = 0, max = 2)
    public double basePoiseRate = 0.025;
    
    @ChangeWarning(Type.RequiresRestart)
    @FloatRange(min = 0, max = 10)
    public double baseStaggerLength = 5;

    @IntegerRange(min = 0, max = 100)
    public int parryWindow = 0;
}
