# プロジェクト紹介：流量安全最適化プラットフォーム

本プロジェクトは、マイクロサービスや分散システム環境において、トラフィック制御・異常検知・設定管理を一貫して行う「流量安全最適化プラットフォーム」です。以下の４つのモジュールで構成され、可用性・セキュリティ・運用性を同時に高めます。

---

## 🌐 1. Nacos ベースの動的コンフィグ＆動的 IP ブラックリスト

- **リアルタイム設定配信**
    - Nacos を設定管理（Config）／サービス管理（Naming）に活用
    - 管理コンソールでルールを追加・編集すると即時反映
- **動的 IP ブラックリスト機能**
    - 短期間に高頻度アクセスを行った IP を自動検知
    - ブラックリストは Nacos 上で有効期限を設定可能
- **運用メリット**
    - 設定変更のためのアプリ再デプロイ不要
    - 集中管理でルールのバージョン管理・履歴追跡が容易

---

## 🔢 2. Redisson カウント制御によるアクセス頻度管理

- **Lua スクリプトによる高性能・原子操作**
  ```lua
  if redis.call('exists', KEYS[1]) == 1 then
      return redis.call('incr', KEYS[1])
  else
      redis.call('set', KEYS[1], 1)
      redis.call('expire', KEYS[1], ARGV[1])
      return 1
  end
  ```
Java 実装例
```java
long count = counterManager.incrAndGetCounter("user:123", 1, TimeUnit.MINUTES);
```
"userId:timeSlot" キー設計で秒・分・時間ごとにカウント可能

カウント上限を超えた場合はリクエスト拒否や遅延応答でフェイルセーフ

Redis の TTL で自動クリアし、メモリリーク防止
## 🔥 3. Sentinel ベースのホットパラメータ限流＆熔断
3.1 ホットパラメータ限流（ParamFlow）
対象パラメータを細かく制御

```java
ParamFlowRule rule = new ParamFlowRule("getProductDetail")
.setParamIdx(0)         // 1番目のパラメータをホットキーとみなす
.setCount(60)           // 1分間に同一キー60回まで
.setDurationInSec(60);
ParamFlowRuleManager.loadRules(Collections.singletonList(rule));
 ```
人気商品の集中リクエストや検索キーワードのスパイクを緩和
特定パラメータのみソフト制限することで他のリクエストに影響を与えない
## 3.2 熔断（Degrade）
スロー・エラー率で自動遮断
 ```java
DegradeRule errorRateRule = new DegradeRule("checkout")
.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO)
.setCount(0.2)          // 例外率20%超でトリガー
.setTimeWindow(30);     // 30秒間遮断
DegradeRuleManager.loadRules(Collections.singletonList(errorRateRule));
 ```
外部API呼び出しの高遅延やエラー集中でマイクロサービスの雪崩を防止
一定期間以内の障害発生をトリガーに自動回復を待機
## 📦 4. Sentinel ルールのローカルファイル持続化
双方向同期
FileRefreshableDataSource で JSON ファイル ← アプリ
FileWritableDataSource でアプリ → JSON ファイル
sentinel/
├── FlowRule.json
├── DegradeRule.json
└── ParamFlowRule.json
 ```java
// WritableDataSource 登録
WritableDataSource<List<FlowRule>> flowWds =
new FileWritableDataSource<>(flowRulePath, JSON::toJSONString);
WritableDataSourceRegistry.registerFlowDataSource(flowWds);
// 初期ルール書き込み
flowWds.write(initialFlowRules);
 ```
## 🛠️ アーキテクチャ概略図
 ```
┌───────────┐       ┌─────────────┐       ┌──────────────┐
│   Nacos   │ ←─── →│Config 数据库 │ ←── → │ Sentinel 控制台│
└───────────┘       └─────────────┘       └──────────────┘
↑                       ↑                    ↑
│                       │                    │
│   ┌───────────┐       │      ┌──────────┐  │
└───│  アプリ群   │───────└─────│  Redis   │──┘
    └───────────┘              └──────────┘
 ```
## 🎯 期待効果
高可用性：トラフィック急増時でも自動フェイルオーバー／熔断

強固なセキュリティ：異常IP遮断・スクレイピング防止

運用効率化：Nacos で一元管理、設定反映の自動化

スケーラビリティ：Redis＋Sentinel による水平拡張対応

