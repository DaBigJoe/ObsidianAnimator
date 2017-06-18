package obsidianAnimations;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.util.ResourceLocation;
import obsidianAPI.animation.wrapper.FunctionAnimationWrapper.IsActiveFunction;
import obsidianAPI.animation.wrapper.IEntityAnimated;
import obsidianAPI.registry.AnimationRegistry;
import obsidianAnimations.entity.saiga.EntitySaiga;

public class CommonProxy
{	
	public void init() 
	{	
		ModEntities.registerEntities();
		registerRendering();
	}

	public void registerRendering() {}

	public void registerAnimations() 
	{
		AnimationRegistry.init();
		//		AnimationRegistry.registerEntity(EntityDummyPlayer.class, "dummy");
		//		AnimationRegistry.registerAnimation("dummy", "WalkF", new ResourceLocation("mod_obsidian_animations:animations/player/WalkF.oba"));


		//		if(swingTime - previousSwingTime > 0.05F) {
		//			if(entity.isSprinting()) {
		//				if(!entity.isCollidedVertically)
		//					state = "SprintJump";
		//				else
		//					state = "SprintF";
		//			}
		//			else if(entity.isSneaking())
		//				state = "CrouchF";
		//			else {
		//				if(!entity.isCollidedVertically)
		//					state = "RunJump";
		//				else
		//					state = "WalkF";
		//			}
		//		}
		//		else if(this.onGround != 0F){
		//			state = "Swing";
		//		}
		//		else if(!entity.isCollidedVertically) {
		//			state = "Jump";
		//		}
		//		else {
		//			if(entity.isSneaking())
		//				state = "CrouchedIdle";
		//			else
		//				state = "Idle";
		//		}	
		
		IsActiveFunction isWalking = (entity) -> { 
			boolean isMoving;
			if(entity instanceof IEntityAnimated)
				isMoving = ((IEntityAnimated) entity).isMoving();
			else
				isMoving = entity.limbSwingAmount > 0.02F;
			return isMoving && !entity.isSprinting() && !entity.isSneaking() && entity.isCollidedVertically;
		};
		IsActiveFunction isSprinting = (entity) -> { 
			boolean isMoving;
			if(entity instanceof IEntityAnimated)
				isMoving = ((IEntityAnimated) entity).isMoving();
			else
				isMoving = entity.limbSwingAmount > 0.02F;
			return isMoving && entity.isSprinting() && entity.isCollidedVertically;
		};
		IsActiveFunction isSneaking = (entity) -> { 
			boolean isMoving;
			if(entity instanceof IEntityAnimated)
				isMoving = ((IEntityAnimated) entity).isMoving();
			else
				isMoving = entity.limbSwingAmount > 0.02F;
			return isMoving && entity.isSneaking() && entity.isCollidedVertically;
		};
		IsActiveFunction isJumping = (entity) -> { 
			boolean isMoving;
			if(entity instanceof IEntityAnimated)
				isMoving = ((IEntityAnimated) entity).isMoving();
			else
				isMoving = entity.limbSwingAmount > 0.02F;
			return !isMoving && !entity.isCollidedVertically;
		};
		IsActiveFunction returnTrue = (iEntityAnimated) -> { 
			return true;
		};

		AnimationRegistry.registerEntity(EntityClientPlayerMP.class, "player");
		AnimationRegistry.registerAnimation("player", "WalkF", new ResourceLocation("mod_obsidian_animations:animations/player/WalkF.oba"), 0, true, isWalking);
		AnimationRegistry.registerAnimation("player", "SprintF", new ResourceLocation("mod_obsidian_animations:animations/player/SprintF.oba"), 1, true, isSprinting);
		AnimationRegistry.registerAnimation("player", "CrouchF", new ResourceLocation("mod_obsidian_animations:animations/player/CrouchF.oba"), 2, true, isSneaking);	
		AnimationRegistry.registerAnimation("player", "Jump", new ResourceLocation("mod_obsidian_animations:animations/player/Jump.oba"), 3, false, 0.0F, isJumping);	
		//AnimationRegistry.registerAnimation("player", "MovementTest", new ResourceLocation("mod_obsidian_animations:animations/player/MovementTest.oba"), 4, returnTrue);	
		//AnimationRegistry.registerAnimation("player", "Idle", new ResourceLocation("mod_obsidian_animations:animations/player/Idle.oba"), 1, returnTrue);
		
		AnimationRegistry.registerEntity(EntitySaiga.class, "saiga");
		AnimationRegistry.registerAnimation("saiga", "Walk", new ResourceLocation("mod_obsidian_animations:animations/saiga/Walk.oba"), 0, true, isWalking);
		AnimationRegistry.registerAnimation("saiga", "Idle", new ResourceLocation("mod_obsidian_animations:animations/saiga/SaigaIdle.oba"), 100, true, returnTrue);
	}

}



