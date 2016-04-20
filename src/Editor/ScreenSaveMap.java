package Editor;

import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;

public class ScreenSaveMap {
	java.awt.TextField textField;

	public ScreenSaveMap(MyEditor myEditor) {

		textField = new java.awt.TextField();
		myEditor.myEditorScreen.loadStyleFile("nifty-default-styles.xml");
		myEditor.myEditorScreen.loadControlFile("nifty-default-controls.xml");
		myEditor.myEditorScreen.addScreen("SaveScreen", new ScreenBuilder(
				"SaveScreen") {
			{
				controller(myEditor.myScreenController);
				layer(new LayerBuilder("Layer_Save") {
					{

						childLayoutCenter();
						panel(new PanelBuilder() {
							{
								childLayoutVertical();
								height("50%");

								control(new TextFieldBuilder("nameMap",
										"Insert ") {
									{
										alignCenter();
										height("8%");
										width("15%");

									}
								});
								control(new ButtonBuilder("idid", "Save") {
									{

										alignCenter();
										width("15%");
										height("10%");
										interactOnClick("SaveMap()");

									}
								});
								control(new ButtonBuilder("idid", "Cancel") {
									{

										alignCenter();
										width("15%");
										height("10%");
										interactOnClick("Return()");

									}
								});

							}
						});

					}
				});

			}
		}.build(myEditor.myEditorScreen));
	}

}
