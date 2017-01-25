/*
 * Copyright 2017 National Bank of Belgium
 *
 * Licensed under the EUPL, Version 1.1 or – as soon they will be approved 
 * by the European Commission - subsequent versions of the EUPL (the "Licence");
 * You may not use this work except in compliance with the Licence.
 * You may obtain a copy of the Licence at:
 * 
 * http://ec.europa.eu/idabc/eupl
 * 
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the Licence is distributed on an "AS IS" basis,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Licence for the specific language governing permissions and 
 * limitations under the Licence.
 */
package ec.nbdemetra.sa.actions;

import ec.nbdemetra.sa.MultiProcessingManager;
import ec.nbdemetra.sa.SaBatchUI;
import ec.nbdemetra.ws.actions.AbstractViewAction;
import ec.tss.sa.SaManager;
import ec.tss.sa.output.BasicConfiguration;
import ec.util.list.swing.JListSelection;
import java.util.ArrayList;
import java.util.List;
import static javax.swing.Action.NAME;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

/**
 *
 * @author Mats Maggi
 */
@ActionID(category = "SaProcessing",
        id = "ec.nbdemetra.sa.actions.CopyComponents")
@ActionRegistration(displayName = "#CTL_CopyComponents", lazy = false)
@ActionReferences({
    @ActionReference(path = MultiProcessingManager.CONTEXTPATH + Edit.PATH, position = 1327)
})
@Messages("CTL_CopyComponents=Copy Components...")
public final class CopyComponents extends AbstractViewAction<SaBatchUI> {

    private final List<String> allFields;
    private JListSelection<String> fieldSelectionComponent;
    
    public CopyComponents() {
        super(SaBatchUI.class);
        refreshAction();
        putValue(NAME, Bundle.CTL_CopyComponents());
        allFields = BasicConfiguration.allSeries(false, SaManager.instance.getProcessors());
        fieldSelectionComponent = new JListSelection<>();
    }

    @Override
    protected void refreshAction() {
        SaBatchUI ui = context();
        enabled = ui != null;
    }

    @Override
    protected void process(SaBatchUI cur) {
        //DemetraUI demetraUI = DemetraUI.getDefault();
        List<String> tmpAvailable = new ArrayList<>(allFields);
        List<String> selectedElements = new ArrayList<>(); //demetraUI.getSelectedSeriesFields();
        tmpAvailable.removeAll(selectedElements);
        selectedElements.forEach(fieldSelectionComponent.getSourceModel()::addElement);
        tmpAvailable.forEach(fieldSelectionComponent.getTargetModel()::addElement);

        NotifyDescriptor d = new NotifyDescriptor(fieldSelectionComponent, "Select fields",
                NotifyDescriptor.OK_CANCEL_OPTION,
                NotifyDescriptor.PLAIN_MESSAGE,
                null,
                NotifyDescriptor.OK_OPTION);
        if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.OK_OPTION) {
            cur.copyComponents(fieldSelectionComponent.getSelectedValues());
        }
    }
}


