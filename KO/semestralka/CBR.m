%% initialization
javaaddpath('./expert.jar');
ex = expert.Expert();

cases = [];
sols = {};
squareSumsCases = [];
squareSols = {};

% different types of trial matches
trials = 20000;
exact = 0;
transformation = 0;
squares = 0;
fail = 0;
invalid = 0;

% result
Dres = ones(5);

%% generate the testing cases
for i=1:trials
    failed = 0;
    
    D = randi(2,5)-1;
% D=[
%   0 0 0 0 0
%   0 1 0 0 0
%   0 0 0 0 0
%   0 0 0 0 0
%   0 0 0 0 0
% ];
% if i > 1
%    D=[
%   0 0 0 0 0
%   0 0 0 0 0
%   0 0 0 0 0
%   1 1 0 0 0
%   1 1 0 0 0
% ]; 
% end


% find out if there is any solution
hint=ex.hint(D);
if(isempty(hint))
    invalid = invalid+1;
    continue;
end


%% get suggested solution
% generate the search vectors
suggestedSolution=[];

Dr0 = D(:)';
Dr1 = rot90(D, 1);
Dr1 = Dr1(:)';
Dr2 = rot90(D, 2);
Dr2 = Dr2(:)';
Dr3 = rot90(D, 3);
Dr3 = Dr3(:)';

Dud = flipud(D);
Dud = Dud(:)';
Dlr = fliplr(D);
Dlr = Dlr(:)';
Ddt = D';
Ddt = Ddt(:)';
Dat = rot90(D', 2);
Dat = Dat(:)';

Dall = [Dr0; Dr1; Dr2; Dr3; Dud; Dlr; Ddt; Dat];

% calculate the square sums
Dsq = [
   sum(D([1:3 6:8 11:13]));
   sum(D([6:8 11:13 16:18]));
   sum(D([11:13 16:18 21:23]));
   sum(D([2:4 7:9 12:14]));
   sum(D([7:9 12:14 17:19]));
   sum(D([12:14 17:19 22:24]));
   sum(D([3:5 8:10 13:15]));
   sum(D([8:10 13:15 18:20]));
   sum(D([13:15 18:20 23:25]));
]';

% search the case database
if(~isempty(cases))
prev=ismember(cases,Dall,'rows');
if(sum(prev) > 0) 
    Cidx=find(prev==1,1);
    C = cases(Cidx, :);
    sol = sols{Cidx};
    
    % find which transformation it was
    transAll = ismember(Dall, C, 'rows');
    transIdx=find(transAll==1,1);
    
    switch(transIdx)
        case 1
            % no transformation needed
        case 2
            sol = rot90(sol, -1);
        case 3
            sol = rot90(sol, -2);
        case 4
            sol = rot90(sol, -3);
        case 5
            sol = flipud(sol);
        case 6
            sol = fliplr(sol);
        case 7
            sol = sol';
        case 8
            sol = rot90(sol', 2);
    end
    
    % give hint
    suggestedSolution=sol;
    
    % correct solution
    if(sum(sol(:)==hint(:)) == 25)
         transformation = transformation+1;
    end
else
    % try to find the solution in the square sums neighbourhood
    [~,idx]=min(pdist2(squareSumsCases, Dsq));
    if(~isempty(idx)) 
        sol = squareSols{idx};
        suggestedSolution = sol;
    
        % correct solution
        if(sum(sol(:)==hint(:)) == 25)
            squares = squares+1;
        end
    end
end
end

% evaluate fails
if(isempty(suggestedSolution)) 
    fail=fail+1;
    failed = 1;
else
    if(sum(suggestedSolution(:)==hint(:)) == 25)
        exact = exact+1;
    else
        fail = fail+1;
        failed = 1;
    end
end

% learn the paths
if(failed)
    cases(end+1,:) = Dr0;
    sols{end+1} = hint;
    squareSumsCases(end+1,:) = Dsq;
    squareSols{end+1} = hint;

    % create the derived configurations based on hint
    Dnext=D;
    for j=1:5
        for k=1:5
            if(hint(j,k) == 1)
                Dnext(j,k) = 1-Dnext(j,k);
                if(j-1 > 1)  Dnext(j-1,k) = 1-Dnext(j-1,k); end
                if(k-1 > 1)  Dnext(j,k-1) = 1-Dnext(j,k-1); end
                if(j+1 <= 5) Dnext(j+1,k) = 1-Dnext(j+1,k); end
                if(k+1 <= 5) Dnext(j,k+1) = 1-Dnext(j,k+1); end
                hint(j,k) = 0;

                cases(end+1,:) = Dnext(:);
                sols{end+1} = hint;

            end
        end
    end

end

    

end

['Trials ' num2str(trials) ' Invalid ' num2str(invalid) ' Exact (transf/squares) ' num2str(exact) ...
    ' (' num2str(transformation) '/' num2str(squares) ') Fail ' num2str(fail)]

% %%
% figure(1);
% plot(hint(:,1), hint(:,2), 'o', 'MarkerSize', 20);
% axis([0 4 0 4]);
% view([-90 90])
