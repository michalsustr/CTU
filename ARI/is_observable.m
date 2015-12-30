function ret = is_observable(A,C)
    O = [C];
    
    for i=1:rank(A)-1
       O = [O; C*A^i];
    end
%    disp(rank(A));
%    disp(rank(O));
    ret = rank(A) == rank(O);
end
