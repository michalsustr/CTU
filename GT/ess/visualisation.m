% load csv files
specs={};
fid = fopen('genomes.csv');
tline = fgetl(fid);
idx = 0;
while ischar(tline)
    idx = idx+1;
    specs{idx} = tline;
    tline = fgetl(fid);
end
fclose(fid);

F = csvread('fractions.csv');

% trim F of redundant data
F=F(:, 1:length(specs));

figure;
clf;
hold on;
plotstyle_poolC = {'bo-', 'go-', 'ro-', 'co-', 'mo-', 'yo-', 'ko-'};
                  %'b:', 'g:', 'r:', 'c:', 'm:', 'y:', 'k:', ...
plotstyle_poolD = {'bx-', 'gx-', 'rx-', 'cx-', 'mx-', 'yx-', 'kx-'};
    
for(i=1:size(F,2)) 
    code=specs(i);
    Ds = sum((specs{i}(:) == '0'));
    Cs = sum((specs{i}(:) == '1'));
    if(Ds > Cs) 
        H(i) = plot(F(:,i), plotstyle_poolD{randi(length(plotstyle_poolD))});
    else 
        H(i) = plot(F(:,i), plotstyle_poolC{randi(length(plotstyle_poolC))});
    end
end

[~,I]=sort(F(57, :), 2, 'descend');
topspecs = I(1:8);
legend(H(topspecs), specs(topspecs));

title('Aging, gameRounds=2000, startingHistory=D')
ylabel('Fraction of population');
xlabel('Simulation round');