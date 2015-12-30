function [classes] = classify_svm(data, model)
%CLASSIFY_SVM Create classification of data based on model
    
    classes.old = data.y;
    new = double((model.w' * data.X+model.b) > 0);
    new(new == 1) = 1;
    new(new == 0) = -1;
    classes.new = new;

end

