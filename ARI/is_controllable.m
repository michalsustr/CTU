function ret = is_controllable(A,B)
    C = [B];
    
    for i=1:rank(A)-1
       C = [C A^i*B];
    end
%    disp(rank(A));
%    disp(rank(C));
    ret = rank(A) == rank(C);
end
