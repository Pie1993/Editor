package Editor;

import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.renderer.ViewPort;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder;

public class ScreenLoadMap {

	public ScreenLoadMap(Nifty nifty, NiftyJmeDisplay display,
			ViewPort viewPort, MyScreenController myScreenController) {
		nifty = display.getNifty();
		nifty.loadStyleFile("nifty-default-styles.xml");
		nifty.loadControlFile("nifty-default-controls.xml");
		nifty.addScreen("LoadScreen", new ScreenBuilder("LoadScreen") {
			{
				controller(myScreenController);
				layer(new LayerBuilder("Layer_Load") {
					{

						childLayoutCenter();
						panel(new PanelBuilder() {
							{
								style("nifty-panel");
								childLayoutVertical();
								height("35%");
								width("35%");
								control(new ListBoxBuilder("listBox") {
									{
										childLayoutVertical();
										alignCenter();
										height("80%");
										width("100%");
										displayItems(15);
										interactOnClick("Return()");
										selectionModeSingle();

									}
								});

								control(new ButtonBuilder("id_open", "Open") {
									{

										alignCenter();
										width("95%");
										height("15%");
										interactOnClick("LoadMap()");

									}
								});
								control(new ButtonBuilder("idid", "Cancel") {
									{

										alignCenter();
										width("95%");
										height("15%");
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
