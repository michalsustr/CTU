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
%%
figure(1);
plot(F);
%legend(specs);

%% Smoothed-out plot

a = 1;
b = [1/4 1/4 1/4 1/4];
Ff = filter(b,a, F);
figure(2);
plot(Ff);;
%legend(specs);
xlabel('Sim step');
ylabel('Fraction of population');