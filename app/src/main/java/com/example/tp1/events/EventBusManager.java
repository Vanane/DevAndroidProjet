package com.example.tp1.events;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class EventBusManager
{
    public static Bus BUS = new Bus(ThreadEnforcer.ANY);
}
