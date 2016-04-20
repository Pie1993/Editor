package Editor;

import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.listbox.builder.ListBoxBuilder;

public class ScreenLoadMap {

	public ScreenLoadMap(MyEditor myEditor) {

		myEditor.myEditorScreen.loadStyleFile("nifty-default-styles.xml");
		myEditor.myEditorScreen.loadControlFile("nifty-default-controls.xml");
		myEditor.myEditorScreen.addScreen("LoadScreen", new ScreenBuilder(
				"LoadScreen") {
			{
				controller(myEditor.myScreenController);
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
		}.build(myEditor.myEditorScreen));

	}

}
