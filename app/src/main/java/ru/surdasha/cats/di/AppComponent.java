package ru.surdasha.cats.di;

import android.content.Context;

import dagger.Component;

@MainScreen
@Component(modules = {NetworkModule.class, CatMainModule.class, ContextModule.class})
public interface AppComponent {

}
