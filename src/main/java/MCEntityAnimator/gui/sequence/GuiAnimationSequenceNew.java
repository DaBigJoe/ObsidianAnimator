package MCEntityAnimator.gui.sequence;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import MCEntityAnimator.Util;
import MCEntityAnimator.animation.AnimationData;
import MCEntityAnimator.animation.AnimationSequence;
import MCEntityAnimator.animation.AnimationStance;


public class GuiAnimationSequenceNew extends GuiScreen 
{
	int posX;
	int posY;
	private String entityName;
	private GuiTextField animationNameTextBar;
	private AnimationStance startingStance;
	
	private boolean flashMessage = false;
	private String flashMessageText = "";
	private int flashMessageTime = 0;

	private static final ResourceLocation texture = new ResourceLocation("mod_MCEA:gui/animation_home.png");

	public GuiAnimationSequenceNew(String par0Str)
	{
		this(par0Str, null);
	}

	public GuiAnimationSequenceNew(String par0Str, AnimationStance par1Stance)
	{
		this.mc = Minecraft.getMinecraft();
		this.entityName = par0Str;
		this.startingStance = par1Stance;
	}
	
	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
	public void initGui()
	{
		super.initGui();
		this.posX = (this.width - 80)/2;
		this.posY = (this.height - 110)/2;
		animationNameTextBar = new GuiTextField(fontRendererObj, posX + 5, posY + 19, 70, 20);
		animationNameTextBar.setText("");
		this.updateButtons();
	}

	public void updateButtons()
	{
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, posX + 5, posY + 41, 70, 20, "Confirm"));
		this.buttonList.add(new GuiButton(1, posX + 5, posY + 63, 70, 20, "Cancel"));
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		animationNameTextBar.textboxKeyTyped(par1, par2);
		super.keyTyped(par1, par2);
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button)
	{
		animationNameTextBar.mouseClicked(mouseX, mouseY, button);
		super.mouseClicked(mouseX, mouseY, button);
	}


	/**
	 * Fired when a control is clicked. This is the equivalent of ActionListener.actionPerformed(ActionEvent e).
	 */
	public void actionPerformed(GuiButton button)
	{	
		switch(button.id)
		{
		case 0:
			AnimationSequence sequence = new AnimationSequence(animationNameTextBar.getText());
			if(AnimationData.addNewSequence(entityName, sequence))
				mc.displayGuiScreen(new GuiAnimationTimeline(entityName, sequence, startingStance)); 
			else
				initFlashMessage("An animation with that name already exists.");
			break;
		case 1: mc.displayGuiScreen(new GuiAnimationSequenceMain(entityName)); break;
		}
	}

	public boolean doesGuiPauseGame()
	{
		return false;
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3)
	{
		this.drawDefaultBackground();
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		this.mc.getTextureManager().bindTexture(texture);		
		Util.drawCustomGui(posX, posY, 80, 110, 0);

		this.drawCenteredString(this.fontRendererObj, "New", posX + 40, posY + 6, 0xffff0000);

		animationNameTextBar.drawTextBox();
		
		if(flashMessage)
		{
			this.drawCenteredString(this.fontRendererObj, flashMessageText, posX + 40, posY - 15, 0xffff0000);
			flashMessageTime++;
			if(flashMessageTime > 200)
			{
				flashMessage = false;
				flashMessageTime = 0;
			}
		}
		
		super.drawScreen(par1, par2, par3);
	}

	private void initFlashMessage(String text)
	{
		flashMessageText = text;
		flashMessage = true;
	}


}


