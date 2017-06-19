package obsidianAPI.animation.wrapper;

import net.minecraft.entity.EntityLivingBase;
import obsidianAPI.animation.AnimationSequence;

public class FunctionAnimationWrapper extends AnimationWrapper {

	private IsActiveFunction isActiveFunction;
	
	public FunctionAnimationWrapper(AnimationSequence animation, int priority, boolean loops, float transitionTime, IsActiveFunction isActiveFunction) {
		super(animation, priority, loops, transitionTime);
		this.isActiveFunction = isActiveFunction;
	}
	
	@Override
	public boolean isActive(EntityLivingBase entity) {
		return isActiveFunction.apply(entity);
	}

	@FunctionalInterface
	public interface IsActiveFunction { 
		public boolean apply (EntityLivingBase entity);
	}
	
}
