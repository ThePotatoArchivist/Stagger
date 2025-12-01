package archives.tater.stagger;

import folk.sisby.kaleido.api.WrappedConfig;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.DisplayNameConvention;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.FloatRange;
import folk.sisby.kaleido.lib.quiltconfig.api.annotations.SerializedNameConvention;
import folk.sisby.kaleido.lib.quiltconfig.api.metadata.NamingSchemes;

@DisplayNameConvention(NamingSchemes.TITLE_CASE)
@SerializedNameConvention(NamingSchemes.SNAKE_CASE)
public class StaggerConfig extends WrappedConfig {
    @FloatRange(min = 0, max = 100)
    public double baseMaxPoise = 20;
    @FloatRange(min = 0, max = 2)
    public double basePoiseRate = 0.025;
    @FloatRange(min = 0, max = 10)
    public double baseStaggerLength = 5;
}
