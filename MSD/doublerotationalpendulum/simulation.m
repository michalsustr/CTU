%% Initial conditions
fi_0 = 0;
th_0 = pi/3;
Dfi_0 = pi/8;
Dth_0 = 0;

%% Start simulation
sim('pendulummodel');

th=Angles.signals.values(:,1);
fi=Angles.signals.values(:,2);
dt=Angles.time(2)-Angles.time(1); % Fixed time step is assumed

x=sin(th).*cos(fi);
y=sin(th).*sin(fi);
z=1-cos(th);

plot3(0,0,1, 'rx', 'MarkerSize', 10); hold on;
axis([-1 1 -1 1 0 1]);

H=plot3(x(1),y(1),z(1));
P=plot3([0 x(1)], [0 y(1)], [1, z(1)], 'r');
O=plot3(x(1), y(1), z(1), 'ro', 'MarkerSize', 10);
for i=2:length(x)
    set(H,'XData',x(1:i),'YData',y(1:i),'ZData',z(1:i));
    set(P,'XData',[0 x(i)],'YData',[0 y(i)],'ZData',[1 z(i)]);
    set(O,'XData',x(i),'YData',y(i),'ZData',z(i));
    pause(dt);
    drawnow;
end