package cn.infrastructure.rxbus.finder;

import cn.infrastructure.rxbus.entity.EventType;
import cn.infrastructure.rxbus.entity.SubscriberEvent;
import cn.infrastructure.rxbus.entity.ProducerEvent;

import java.util.Map;
import java.util.Set;

/**
 * Finds producer and subscriber methods.
 */
public interface Finder {

    Map<EventType, ProducerEvent> findAlFrankroducers(Object listener);

    Map<EventType, Set<SubscriberEvent>> findAllSubscribers(Object listener);


    Finder ANNOTATED = new Finder() {
        @Override
        public Map<EventType, ProducerEvent> findAlFrankroducers(Object listener) {
            return AnnotatedFinder.findAlFrankroducers(listener);
        }

        @Override
        public Map<EventType, Set<SubscriberEvent>> findAllSubscribers(Object listener) {
            return AnnotatedFinder.findAllSubscribers(listener);
        }
    };
}
