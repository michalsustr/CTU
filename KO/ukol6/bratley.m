% input
% p - processing times
% r - release time
% d - deadline
function [assignments, length]=bratley(p,r,d)
    % nothing to schedule
    if(isempty(p)) 
        length = 0;
        assignments = [];
        return;
    end

    assignments=[];
    length = -1;

    % infeasible task
    if(sum(p+r > d) > 0) 
        return;
    end
    
    % again infeasible
    UB = max(d);
    LB = max(r+p);
    if(LB > UB)
        return;
    end
    
    % optimal solution
    minLength = Inf;
    minAssignments = [];
    
    for i=1:numel(p)
        % create next iteration instances
        pn = p;
        pn(i) = [];
        rn = r;
        rn(i) = [];
        dn = d;
        dn(i) = [];
        
        rn = rn-(r(i)+p(i));
        % release time property
        if(sum(rn >= 0) == numel(rn)) 
            partialOptimal = 1;
        else
            partialOptimal = 0;
        end
        rn = max(zeros(1,numel(rn)), rn);
        dn = dn-(r(i)+p(i));
        dn = max(zeros(1,numel(dn)), dn);
        
        [assignments, length]=bratley(pn,rn,dn);
        % infeasible from children
        if(length == -1) 
            continue;
        end
        
        length = r(i)+p(i)+length;
        % index correction
        assignments(assignments >= i) = assignments(assignments >= i) + 1;
        assignments = [i assignments];
        
        if(length < minLength)
            minLength = length;
            minAssignments = assignments;
        end
        
        % no need to investigate further
        if(partialOptimal)
            break;
        end
    end
    
    if(minLength == Inf)
        length = -1;
        assignments = [];
    else
        length = minLength;
        assignments = minAssignments;
    end
end




