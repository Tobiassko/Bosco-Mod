// Made with Blockbench 5.0.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class bosco extends EntityModel<Entity> {
	private final ModelPart main;
	private final ModelPart Arm_Right;
	private final ModelPart ElbowRight;
	private final ModelPart RightHand;
	private final ModelPart Arm_Left;
	private final ModelPart ElbowLeft;
	private final ModelPart LeftHand;
	public bosco(ModelPart root) {
		this.main = root.getChild("main");
		this.Arm_Right = this.main.getChild("Arm_Right");
		this.ElbowRight = this.Arm_Right.getChild("ElbowRight");
		this.RightHand = this.ElbowRight.getChild("RightHand");
		this.Arm_Left = this.main.getChild("Arm_Left");
		this.ElbowLeft = this.Arm_Left.getChild("ElbowLeft");
		this.LeftHand = this.ElbowLeft.getChild("LeftHand");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData main = modelPartData.addChild("main", ModelPartBuilder.create().uv(0, 17).cuboid(3.8229F, 3.1F, -2.9537F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-1.2771F, -4.0F, -1.9537F, 7.0F, 7.0F, 4.0F, new Dilation(-0.01F))
		.uv(0, 11).cuboid(-2.2771F, 3.0F, -1.9537F, 6.0F, 2.0F, 4.0F, new Dilation(0.0F))
		.uv(16, 17).cuboid(-2.2771F, -4.0F, -2.6537F, 6.0F, 8.0F, 1.0F, new Dilation(0.0F))
		.uv(28, 26).cuboid(3.7229F, -4.0F, -2.6537F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F))
		.uv(22, 0).cuboid(-2.2771F, -4.0F, 1.8463F, 6.0F, 8.0F, 1.0F, new Dilation(0.0F))
		.uv(30, 16).cuboid(3.7229F, -4.0F, 1.8463F, 2.0F, 7.0F, 1.0F, new Dilation(0.0F))
		.uv(16, 26).cuboid(-2.2771F, 5.0F, -1.9537F, 2.0F, 1.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 12.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

		ModelPartData cube_r1 = main.addChild("cube_r1", ModelPartBuilder.create().uv(20, 11).cuboid(0.9659F, -0.7412F, -1.2F, 4.0F, 1.0F, 4.0F, new Dilation(-0.01F)), ModelTransform.of(-1.2771F, 6.0F, -0.7537F, 0.0F, 0.0F, -0.2618F));

		ModelPartData cube_r2 = main.addChild("cube_r2", ModelPartBuilder.create().uv(36, 12).cuboid(0.5F, -1.5657F, -0.4343F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7771F, 1.0F, -0.7537F, -0.7854F, 0.0F, 0.0F));

		ModelPartData cube_r3 = main.addChild("cube_r3", ModelPartBuilder.create().uv(12, 36).cuboid(0.5F, -1.5657F, -0.4343F, 1.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.7771F, -2.0F, -0.7537F, -0.7854F, 0.0F, 0.0F));

		ModelPartData Arm_Right = main.addChild("Arm_Right", ModelPartBuilder.create().uv(22, 9).cuboid(0.499F, -0.5436F, 0.3F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 25).cuboid(-0.001F, -1.5436F, 1.3F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.2771F, 5.0F, 1.7463F, 0.0F, 0.0F, 0.0436F));

		ModelPartData ElbowRight = Arm_Right.addChild("ElbowRight", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 6.4F, 1.5F));

		ModelPartData cube_r4 = ElbowRight.addChild("cube_r4", ModelPartBuilder.create().uv(34, 24).cuboid(0.9537F, -6.6993F, 0.3F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		ModelPartData RightHand = ElbowRight.addChild("RightHand", ModelPartBuilder.create().uv(36, 0).cuboid(-1.001F, 2.8365F, -0.2F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(24, 34).cuboid(-1.001F, -0.0635F, -0.2F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.4544F, -6.9F, 0.0F));

		ModelPartData cube_r5 = RightHand.addChild("cube_r5", ModelPartBuilder.create().uv(36, 6).cuboid(-0.0414F, -4.2847F, -1.2F, 1.0F, 4.0F, 2.0F, new Dilation(0.01F)), ModelTransform.of(-1.0F, 6.8801F, 1.0F, 0.0F, 0.0F, 0.245F));

		ModelPartData Arm_Left = main.addChild("Arm_Left", ModelPartBuilder.create().uv(22, 9).cuboid(0.499F, -0.5436F, 0.3F, 1.0F, 1.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 25).cuboid(-0.001F, -1.5436F, -1.7F, 2.0F, 8.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(-2.2771F, 5.0F, -3.2537F, 0.0F, 0.0F, 0.0436F));

		ModelPartData ElbowLeft = Arm_Left.addChild("ElbowLeft", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 6.4F, -1.5F));

		ModelPartData cube_r6 = ElbowLeft.addChild("cube_r6", ModelPartBuilder.create().uv(34, 24).cuboid(0.9537F, -6.6993F, 0.3F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		ModelPartData LeftHand = ElbowLeft.addChild("LeftHand", ModelPartBuilder.create().uv(36, 0).cuboid(-1.001F, 2.8365F, -0.2F, 1.0F, 4.0F, 2.0F, new Dilation(0.0F))
		.uv(24, 34).cuboid(-1.001F, -0.0635F, -0.2F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.4544F, -6.9F, 0.0F));

		ModelPartData cube_r7 = LeftHand.addChild("cube_r7", ModelPartBuilder.create().uv(36, 6).cuboid(-0.0414F, -4.2847F, -1.2F, 1.0F, 4.0F, 2.0F, new Dilation(0.01F)), ModelTransform.of(-1.0F, 6.8801F, 1.0F, 0.0F, 0.0F, 0.245F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		main.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}