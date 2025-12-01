package archives.tater.stagger;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.*;
import folk.sisby.kaleido.lib.quiltconfig.api.metadata.ChangeWarning.Type;
import folk.sisby.kaleido.lib.quiltconfig.api.metadata.NamingSchemes;

@DisplayNameConvention(NamingSchemes.TITLE_CASE)
@SerializedNameConvention(NamingSchemes.SNAKE_CASE)
public class StaggerConfig extends WrappedConfig {
    @ChangeWarning(Type.RequiresRestart)
    @IntegerRange(min = 0, max = 100)
    public int baseMaxPoise = 20;

    @ChangeWarning(Type.RequiresRestart)
    @FloatRange(min = 0, max = 2)
    public double basePoiseRate = 0.025;
    
    @ChangeWarning(Type.RequiresRestart)
    @FloatRange(min = 0, max = 10)
    public double baseStaggerLength = 5;
}
