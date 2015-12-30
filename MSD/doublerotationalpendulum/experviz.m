load experimenty;

%% Napajeni motoru - PWM: f = 25.3 kHz, Umax = 14.4 V, Umin = -14.4 V
plot(pwm(:,1),pwm(:,2));
xlabel('Ridici napeti (V)');
ylabel('Cinitel plneni (%)');

%% Sila - mereno na rameni 191 mm (chyba +- 0.1N)
plot(sila(:,1),sila(:,2));
xlabel('Ridici napeti (V)');
ylabel('Sila (N)');

%% Rameno bez zateze
AX = plotyy(bezzateze.time,bezzateze.signals.values(:,1),...
    bezzateze.time,bezzateze.signals.values(:,2));
set(get(AX(1),'Ylabel'),'String','Ridici napeti (V)');
set(get(AX(2),'Ylabel'),'String','Natoceni (-)');
xlabel('Cas (s)');

%% Rameno se zavazim
AX = plotyy(sezavazim.time,sezavazim.signals.values(:,1),...
    sezavazim.time,sezavazim.signals.values(:,2));
set(get(AX(1),'Ylabel'),'String','Ridici napeti (V)');
set(get(AX(2),'Ylabel'),'String','Natoceni (-)');
xlabel('Cas (s)');

%% Otaceni se senzory - priblizne 1 otacka (CCW) pak pauza a znovu
plot(otacky.time,otacky.signals.values(:,2),...
    otacky.time,otacky.signals.values(:,3),...
    otacky.time,otacky.signals.values(:,4));
xlabel('Cas (s)');
ylabel('Natoceni (-)');
legend('Zakladna','Kyvadlo 1','Kyvadlo 2');    

%% Pouze 1. kyvadlo - volne (strceni, umele uklidneni, pauza)
% motor i pri nulovem ridicim napeti brzdi
plot(kyvadlo1vol.time,kyvadlo1vol.signals.values(:,2),...
    kyvadlo1vol.time,kyvadlo1vol.signals.values(:,3));
xlabel('Cas (s)');
ylabel('Natoceni (-)');
legend('Zakladna','Kyvadlo 1');    

%% Pouze 1. kyvadlo - hnane
plot(kyvadlo1.time,kyvadlo1.signals.values(:,1),...
    kyvadlo1.time,kyvadlo1.signals.values(:,2),...
    kyvadlo1.time,kyvadlo1.signals.values(:,3));
xlabel('Cas (s)');
ylabel('Napeti (V), Natoceni (-)');
legend('Napeti','Zakladna','Kyvadlo 1');    

%% Obe kyvadla - 2. volne, 1. a zakladna zafixovane
plot(kyvadlo2fix.time,kyvadlo2fix.signals.values(:,2),...
    kyvadlo2fix.time,kyvadlo2fix.signals.values(:,3),...
    kyvadlo2fix.time,kyvadlo2fix.signals.values(:,4));
xlabel('Cas (s)');
ylabel('Natoceni (-)');
legend('Zakladna','Kyvadlo 1','Kyvadlo 2'); 

%% Obe kyvadla - kyvadla volna a zakladna zafixovane
plot(kyvadlo2fixB.time,kyvadlo2fixB.signals.values(:,2),...
    kyvadlo2fixB.time,kyvadlo2fixB.signals.values(:,3),...
    kyvadlo2fixB.time,kyvadlo2fixB.signals.values(:,4));
xlabel('Cas (s)');
ylabel('Natoceni (-)');
legend('Zakladna','Kyvadlo 1','Kyvadlo 2'); 

%% Obe kyvadla - volne
plot(kyvadlo2vol.time,kyvadlo2vol.signals.values(:,2),...
    kyvadlo2vol.time,kyvadlo2vol.signals.values(:,3),...
    kyvadlo2vol.time,kyvadlo2vol.signals.values(:,4));
xlabel('Cas (s)');
ylabel('Natoceni (-)');
legend('Zakladna','Kyvadlo 1','Kyvadlo 2'); 

%% Obe kyvadla - hnane
plot(kyvadlo2.time,kyvadlo2.signals.values(:,1),...
    kyvadlo2.time,kyvadlo2.signals.values(:,2),...
    kyvadlo2.time,kyvadlo2.signals.values(:,3),...
    kyvadlo2.time,kyvadlo2.signals.values(:,4));
xlabel('Cas (s)');
ylabel('Napeti (V), Natoceni (-)');
legend('Napeti','Zakladna','Kyvadlo 1','Kyvadlo 2');



