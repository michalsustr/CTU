A = [ones(size(in,1),1), in(:,1)];
b = in(:,2);
x = pinv(A)*b;
x0 = x(1);
x1 = x(2);

% aproximovana funkcia
y = x0+x1*(in(:,1));

hold on;
xlabel('vzdialenost [mm]');
ylabel('napatie [V]');
plot(in(:,1), in(:,2), 'r');
plot(in(:,1), y, 'g');
hold off;
disp('offset');
disp(x0);
disp('smernica');
disp(x1);