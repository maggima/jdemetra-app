/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.nbdemetra.ui.actions;

import org.netbeans.api.actions.Closable;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle.Messages;

@ActionID(category = "File", id = "ec.nbdemetra.ui.actions.CloseAction")
@ActionRegistration(displayName = "#CloseAction", lazy = false)
@Messages("CloseAction=Close")
public final class CloseAction extends AbilityAction<Closable> {

    public CloseAction() {
        super(Closable.class);
    }

    @Override
    protected void performAction(Iterable<Closable> items) {
        for (Closable o : items) {
            o.close();
        }
    }

    @Override
    public String getName() {
        return Bundle.CloseAction();
    }
}
