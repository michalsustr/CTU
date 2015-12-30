function [x,y] = num_repetition(A)
%NUM_REPETITION 
%   Return how many times y(i) number x(i) occured in matrix A
a=A(:);
count = zeros(max(a)-min(a),2);
for i = min(a): max(a)
    count(i,1) = i;
    for j = 1: length(a)
        if(a(j) == i)
            count(i,2) = count(i,2) + 1;
        end
    end
end
x=count(find(count(:,2) > 0),1);
y=count(find(count(:,2) > 0),2);
end

