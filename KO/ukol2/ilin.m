%% Part 1)

disp('PART 1');

% min sum of x_i from i=1 to 24
A = zeros(24);
for i=1:7
    A(i,:) = ones(1,24);
    A(i,(i+1):(i+16)) = zeros(1, 16);
end
for i=8:24
    A(i,(i-7):i) = ones(1,8);
end

b = [6 6 6 6 6 8 9 12 18 22 25 21 21 20 18 21 21 24 24 18 18 18 12 8]';
c = ones(24,1);

sense=1;                %sense of optimization: 1=minimization, -1=maximization
ctype = repmat(['G'], 24, 1);   %constraint type: 'E'="=", 'L'="<=", 'G'=">="
lb = zeros(24,1);               %lower bound of the variables
ub = inf(24,1);                 %upper bound of the variables
vartype = repmat(['C'], 24, 1); %variable type: 'C'=continuous, 'I'=integer

schoptions=schoptionsset('ilpSolver','glpk','solverVerbosity',2);

% call command for ILP
[xmin,fmin,status,extra] = ilinprog(schoptions,sense,c,A,b,ctype,lb,ub,vartype);
% show the solution
if(status==1)
    j = [];
    for i = 0:7,
        j = [j; [24-i+1:24, 1:24-i]];
    end;
     br = sum(xmin(j'),2);
     subplot(2,1,1); 
    bar([b,br]);
    barmap=[0 1 0;1 1 0];
    colormap(barmap);
    legend('Task (Personnal demand)', 'Calculated');
    fprintf('Personnel count:%d\n', sum(xmin(1:24)));
    fprintf('Total diff:%d\n',     sum(abs(br - b)));
    xmin(1:24)'
else
    disp('Problem nema riesenie!');
end;

%% Part 2)

disp('PART 2');

% min sum of x_i from i=1 to 24
A = zeros(24);
for i=1:7
    A(i,:) = ones(1,24);
    A(i,(i+1):(i+16)) = zeros(1, 16);
end
for i=8:24
    A(i,(i-7):i) = ones(1,8);
end

b = [6 6 6 6 6 8 9 12 18 22 25 21 21 20 18 21 21 24 24 18 18 18 12 8]';
c = [zeros(24, 1);ones(24,1)];


bb = [-b;b];
A = [   
    [-A eye(24)];
	[A,eye(24)];
];

sense=1;                %sense of optimization: 1=minimization, -1=maximization
ctype = [repmat('G',24*2,1)];   %constraint type: 'E'="=", 'L'="<=", 'G'=">="
lb = zeros(48,1);               %lower bound of the variables
ub = inf(48,1);                 %upper bound of the variables
vartype = [repmat(['I'], 24, 1); repmat(['C'], 24, 1)]; %variable type: 'C'=continuous, 'I'=integer

schoptions=schoptionsset('ilpSolver','glpk','solverVerbosity',2);

% call command for ILP
[xmin,fmin,status,extra] = ilinprog(schoptions,sense,c,A,bb,ctype,lb,ub,vartype);
% show the solution
if(status==1)
    j = [];
    for i = 0:7,
        j = [j; [24-i+1:24, 1:24-i]];
    end;
     br = sum(xmin(j'),2);
     subplot(2,1,2); 
    bar([b,br]);
    barmap=[0 1 0;1 1 0];
    colormap(barmap);
    legend('Task (Personnal demand)', 'Calculated');
    fprintf('Personnel count:%d\n', sum(xmin(1:24)));
    fprintf('Total diff:%d\n', sum(abs(br - b)));
    xmin(1:24)'
else
    disp('Problem nema riesenie!');
end;
