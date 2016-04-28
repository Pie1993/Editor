package Editor;

import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;

public class ScreenSaveMap {
	java.awt.TextField textField;

	public ScreenSaveMap(Nifty nifty, NiftyJmeDisplay display,
			ViewPort viewPort, MyScreenController myScreenController) {

		nifty = display.getNifty();
		textField = new java.awt.TextField();
		nifty.loadStyleFile("nifty-default-styles.xml");
		nifty.loadControlFile("nifty-default-controls.xml");
		nifty.addScreen("SaveScreen", new ScreenBuilder("SaveScreen") {
			{
				controller(myScreenController);
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
										interactOnClick("saveMap()");

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
		}.build(nifty));
	}

}
