package Editor;

import de.lessvoid.nifty.builder.ImageBuilder;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

public class ScreenNiftyCube {

	public ScreenNiftyCube(MyEditor myEditor) {

		myEditor.myEditorScreen = myEditor.display.getNifty();
		myEditor.myEditorScreen.setIgnoreKeyboardEvents(true);
		myEditor.getGuiViewPort().addProcessor(myEditor.display);
//
//		myEditor.getFlyByCamera().setDragToRotate(false);
		

		myEditor.myEditorScreen.loadStyleFile("nifty-default-styles.xml");
		myEditor.myEditorScreen.loadControlFile("nifty-default-controls.xml");

		myEditor.myEditorScreen.addScreen("CubeScreen", new ScreenBuilder(
				"CubeScreen") {
			{
				controller(myEditor.myScreenController); // Screen properties

				layer(new LayerBuilder("Layer_ID") {
					{
						childLayoutHorizontal();
						width("100%");
						panel(new PanelBuilder("Panel_ID") {
							{
								childLayoutHorizontal();
								height("100%");
								width("50%");
								control(new ButtonBuilder("", "Open Map") {
									{
										height("5%");
										width("20%");
										interactOnClick("LoadScreen()");

									}
								});
								control(new ButtonBuilder("", "Save Map") {
									{
										height("5%");
										width("20%");
										interactOnClick("SaveScreen()");
									}
								});

							}
						}); // 1111
						panel(new PanelBuilder("Panel_ID") {
							{
								childLayoutHorizontal();
								height("100%");
								width("50%");
								panel(new PanelBuilder("Panel_ID") {
									{
										childLayoutHorizontal();
										height("100%");
										width("50%");
									}
								}); // /2222-111111
								panel(new PanelBuilder("Panel_ID") {
									{
										childLayoutHorizontal();
										height("100%");
										width("50%"); // /2222-22222
										panel(new PanelBuilder("Panel_ID") {
											{
												childLayoutHorizontal();
												height("100%");
												width("50%");
											}
										}); // /2222-22222-11111
										panel(new PanelBuilder("Panel_ID") {
											{
												childLayoutHorizontal();
												height("100%");
												width("50%");
												panel(new PanelBuilder(
														"Panel_ID") {
													{
														childLayoutHorizontal();
														height("100%");
														width("50%");
													}
												}); // /2222-2222-111111
												panel(new PanelBuilder(
														"Panel_ID") {
													{
														childLayoutVertical();
														alignLeft();
														height("100%");
														width("100%");
														image(new ImageBuilder() {
															{
																alignLeft();
																height("15%");
																width("50%");
																filename("Textures/ScreenGreen.png");
															}
														});

														control(new ButtonBuilder(
																"Button",
																"Grass Cube") {
															{
																alignLeft();
																height("5%");
																width("50%");
																interactOnClick("GrassCube()");

															}
														});
														image(new ImageBuilder() {
															{
																alignLeft();
																height("15%");
																width("50%");
																filename("Textures/ScreenStone.png");
															}
														});
														control(new ButtonBuilder(
																"Button",
																"Stone Cube") {
															{
																alignLeft();
																height("5%");
																width("50%");
																interactOnClick("StoneCube()");
															}
														});
														image(new ImageBuilder() {
															{
																alignLeft();
																height("15%");
																width("50%");
																filename("Textures/ScreenWall.png");
															}
														});
														control(new ButtonBuilder(
																"Button",
																"Wall Cube") {
															{
																alignLeft();
																height("5%");
																width("50%");
																interactOnClick("WallCube()");

															}
														});
														image(new ImageBuilder() {
															{
																alignLeft();
																height("15%");
																width("50%");
																filename("Textures/ScreenWood.png");
															}
														});
														control(new ButtonBuilder(
																"Button",
																"Wood Cube") {
															{
																alignLeft();
																height("5%");
																width("50%");
																interactOnClick("WoodCube()");

															}
														});
														image(new ImageBuilder() {
															{
																alignLeft();
																height("15%");
																width("50%");
																filename("Textures/trasp.jpg");
															}
														});
														control(new ButtonBuilder(
																"Button",
																"Transparent") {
															{
																alignLeft();
																height("5%");
																width("50%");
																interactOnClick("TransparentCube()");

															}
														});
													}
												});
											}
										});
									}
								});
							}
						});
					}
				}); // layer

			}
		}.build(myEditor.myEditorScreen));

		myEditor.myEditorScreen.gotoScreen("CubeScreen"); // start the screen
	

	}

}
