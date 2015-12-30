function A=enter_points()
i=1;
A=[0,0];
img = figure;
hold on;
axis([-1 1 -1 1]);
while true
    
    [x,y,but] = ginput(1);
    if isempty(but)
       save('points.mat', 'A');
       close(img);
       disp('Zadane body boli ulozene do suboru points.mat v matici A');
       break;
    end
    
    A(i,:) = [x,y];
    plot(x,y,'o');
    i=i+1;
end


end
