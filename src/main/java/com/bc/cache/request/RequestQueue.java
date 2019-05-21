package com.bc.cache.request;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class RequestQueue {
    /**
     * 内存队列
     */
    private List<ArrayBlockingQueue<Request>> queues = new ArrayList<>();

    /**
     * 标识位map
     */
    private Map<Integer, Boolean> flagMap = new ConcurrentHashMap<>();

    private static class Singleton {
        private static RequestQueue instance;

        static {
            instance = new RequestQueue();
        }

        public static RequestQueue getInstance() {
            return instance;
        }
    }

    public static RequestQueue getInstance() {
        return Singleton.getInstance();
    }


    public void addQueue(ArrayBlockingQueue<Request> queue) {
        this.queues.add(queue);
    }

    public int getQueueSize() {
        return this.queues.size();
    }

    public ArrayBlockingQueue<Request> getQueue(int index) {
        return this.queues.get(index);
    }

    public Map<Integer, Boolean> getFlagMap() {
        return this.flagMap;
    }
}
