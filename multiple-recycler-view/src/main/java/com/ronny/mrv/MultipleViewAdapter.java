/**
 * MIT License
 * <p>
 * Copyright (c) 2019 吴荣 (Ronny Wu)
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ronny.mrv;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * TODO 封装目标:
 * <p>
 * 1. 编译期可以自动生成注册代码.
 * 2. 自动支持多布局,返回多少个布局ID,自动注册多少个布局,至少一个.
 * 3. 支持Manager管理.
 * 4. 支持多Manager聚合管理.
 * 5. 支持下拉刷新,上拉加载更多.
 * 6. 支持自定义下拉刷新头和上拉加载更多脚.
 * 7. 支持自动刷新和自动加载更多.
 * 8. 支持两种侧滑模式的菜单.
 * 9. 多类型事件支持.
 * 10. 顺序数据源的聚合事件支持.
 * 11. 线条的支持.
 * 12. 多类型布局的支持.
 * 13. 自动生命周期期间mapping的自动维护,降低内存消耗.
 * 14. 多类型下标支持,完整下标,聚合下标,分类下标.
 * 15. 同分类的无序数据源聚合支持.
 * 16. 自动分类间隔支持,自动分类间隔配置.
 * 17. 回收事件处理,可配置某个位置的Item不回收,或者回收以后,自动缓存,避免复杂布局的重建影响列表的性能.
 * 18. 自动占位及异步刷新支持.
 * 19. 基于状态机的页面状态管理.(可单独成库,然后提供一个兼容库,可以自动集成到当前库中进行使用.)
 */
public class MultipleViewAdapter extends RecyclerView.Adapter {

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}