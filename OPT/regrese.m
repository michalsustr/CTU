[y,Fs]=wavread('gong.wav');
%sound(y,Fs);
M = ones(size(y,1)-300,300);
%stanoví velikost matice
for t=300:size(y)
for i=1:299
M(t-299,i+1)=y(t-i);
end
end
%naplní matici
x=M\y(300:size(y));
%vyøeí opt. úlohu
ytylda =(zeros(1,size(y,1)-300));
%pro násl. algorytmus je podstatné aby ytylda byla 0vý vektor
 for i=1:67396
    for j=1:300
        ytylda(i) =ytylda(i) +(M(i,j)*x(j));
    end
end
hold on
plot(301:1:size(y,1),y(301:size(y,1)), 'r')
plot(301:1:size(y,1),ytylda, 'b')
xlabel('vzorek');
ylabel('hodnota vzorku');
title('pravý gong/syntetický gong');
hold off
%v pøíloze posílám graf synt. gongu posunutý o 1 protoe jinak se dokonale
%pøekryjí
%sound(y,Fs)
%sound(ytylda,Fs)
%omlouvám se moná jsem se nìkde upsal kdy jsem nìco pøepisoval ale vylo
%mi e sound(y,Fs) a sound(ytylda-1,Fs) jsou nerozeznatelné uchem ... take
%ve nasvìdèuje tomu e je ve v poøádku