package com.debdasha.catsapp.di.components;

import com.debdasha.catsapp.di.modules.AndroidModule;
import com.debdasha.catsapp.di.modules.ContextModule;
import com.debdasha.catsapp.di.modules.DataModule;
import com.debdasha.catsapp.di.modules.DomainModule;
import com.debdasha.catsapp.di.modules.UIModule;
import com.debdasha.catsapp.di.scopes.PerApplication;

import dagger.Component;

@PerApplication
@Component(modules = {AndroidModule.class, ContextModule.class,
        DataModule.class, DomainModule.class})
public interface AppComponent {
    UIComponent addUIComponent(UIModule module);

    ContextModule contextModule(ContextModule context);
}
