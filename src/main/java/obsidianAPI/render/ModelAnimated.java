package obsidianAPI.render;

import java.io.IOException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import obsidianAPI.EntityAnimationProperties;
import obsidianAPI.animation.AnimationSequence;
import obsidianAPI.registry.AnimationRegistry;
import obsidianAnimator.Util;

public class ModelAnimated extends ModelObj
{

	long animStartTime = System.nanoTime();

	public ModelAnimated(String entityName, ResourceLocation modelLocation, ResourceLocation textureLocation) throws IOException
	{			
		super(entityName, Minecraft.getMinecraft().getResourceManager().getResource(modelLocation).getInputStream(), textureLocation);
	}

	@Override
	public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity) 
	{				
		super.setRotationAngles(f, f1, f2, f3, f4, f5, entity);

		EntityAnimationProperties animProps = (EntityAnimationProperties) entity.getExtendedProperties("Animation");
		if(animProps != null)
		{
			AnimationSequence seq = animProps.getActiveAnimation();			
			//float time = Util.getAnimationFrameTime(animProps.getAnimationStartTime(), 0, seq.getFPS(), 1.0F);
			float time = Util.getAnimationFrameTime(animStartTime, 0, seq.getFPS(), 1.0F);

			if(time > seq.getTotalTime())
				animStartTime = System.nanoTime();


			seq.animateAll(time, this);	
		}
	}
}
