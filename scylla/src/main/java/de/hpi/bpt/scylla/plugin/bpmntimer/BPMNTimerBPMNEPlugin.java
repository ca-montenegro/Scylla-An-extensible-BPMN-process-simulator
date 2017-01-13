package de.hpi.bpt.scylla.plugin.bpmntimer;

import java.util.Map;

import de.hpi.bpt.scylla.exception.ScyllaRuntimeException;
import de.hpi.bpt.scylla.model.process.ProcessModel;
import de.hpi.bpt.scylla.plugin_type.simulation.event.BPMNIntermediateEventPluggable;
import de.hpi.bpt.scylla.simulation.ProcessInstance;
import de.hpi.bpt.scylla.simulation.event.BPMNIntermediateEvent;
import de.hpi.bpt.scylla.simulation.event.ScyllaEvent;
import desmoj.core.simulator.TimeSpan;

public class BPMNTimerBPMNEPlugin extends BPMNIntermediateEventPluggable {

    @Override
    public String getName() {
        return BPMNTimerPluginUtils.PLUGIN_NAME;
    }

    @Override
    public void eventRoutine(BPMNIntermediateEvent desmojEvent, ProcessInstance processInstance) throws ScyllaRuntimeException {

        ProcessModel processModel = processInstance.getProcessModel();
        Map<Integer, ScyllaEvent> nextEvents = desmojEvent.getNextEventMap();
        for (int index : nextEvents.keySet()) {
            ScyllaEvent event = nextEvents.get(index);
            int nextNodeId = event.getNodeId();

            TimeSpan timeSpan = BPMNTimerPluginUtils.getTimeSpanUntilNextEvent(processModel, nextNodeId);
            if (timeSpan != null) {
                desmojEvent.getTimeSpanToNextEventMap().put(index, timeSpan);
            }
        }
    }

}
